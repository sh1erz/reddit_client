package com.example.redditclient.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.example.redditclient.data.db.PostDb
import com.example.redditclient.model.PostData
import io.reactivex.Observable

@ExperimentalPagingApi
class RedditRepository(val redditApiService: RedditApiService, val postDb: PostDb) {

    fun getPosts(pagingConfig: PagingConfig = getDefaultPageConfig()): Observable<PagingData<PostData>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = PostMediator(redditApiService, postDb)
        ) {
            postDb.postDao().getAllPosts()
        }.observable
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    companion object{
        const val DEFAULT_PAGE_SIZE = 10
    }
}