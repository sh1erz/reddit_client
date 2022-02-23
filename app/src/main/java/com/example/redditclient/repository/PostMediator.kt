package com.example.redditclient.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.redditclient.data.db.PostDb
import com.example.redditclient.data.db.RemoteKey
import com.example.redditclient.model.PostData
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class PostMediator(private val postService: RedditApiService, private val postDb: PostDb) :
    RemoteMediator<Int, PostData>() {

    private val postDao = postDb.postDao()
    private val remoteKeyDao = postDb.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostData>
    ): MediatorResult {
        val key: String? = when (loadType) {
            LoadType.REFRESH -> {
                Log.d(DEBUG, "load: refresh")
                null
            }
            LoadType.PREPEND -> {
                Log.d(DEBUG, "load: prepend")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                Log.d(DEBUG, "load: append")
                if (state.pages.flatMap { it.data }.size >= 50) {
                    Log.d(DEBUG, "load: pages >= 50")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                val remoteKey = postDb.withTransaction {
                    remoteKeyDao.remoteKey()
                }
                if (remoteKey.nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKey.nextKey
            }
        }

        try {
            val listingData = postService.getPosts(
                after = key
            ).data
            val posts = listingData.children.map { it.data }
            postDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postDao.deleteAllPosts()
                    remoteKeyDao.deleteRemoteKey()
                }
                remoteKeyDao.updateKey(
                    RemoteKey(
                        nextKey = listingData.after
                    )
                )
                postDao.insertAllPosts(posts)
            }
            return MediatorResult.Success(endOfPaginationReached = posts.isEmpty())
        } catch (exception: IOException) {
            Log.i(DEBUG, "load: ${exception.message}")
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            Log.i(DEBUG, "load: ${exception.message}")
            return MediatorResult.Error(exception)
        }
    }

}

const val DEBUG = "debug_reddit"