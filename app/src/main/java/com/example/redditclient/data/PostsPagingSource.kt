package com.example.redditclient.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.redditclient.model.PostData
import com.example.redditclient.repository.RedditApiService
import retrofit2.HttpException
import java.io.IOException

class PostsPagingSource(val redditApiService: RedditApiService) : PagingSource<String, PostData>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostData> {
        val after = params.key
        return try {
            val listingData = redditApiService.getPosts(after = after).data
            val posts = listingData.children.map { it.data }
            LoadResult.Page(
                posts, prevKey = listingData.before,
                nextKey = listingData.after
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, PostData>): String? {
        return state.anchorPosition?.let{state.closestPageToPosition(it)?.prevKey}
    }

}