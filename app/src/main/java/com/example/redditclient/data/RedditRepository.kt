package com.example.redditclient.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.example.redditclient.model.PostData
import com.example.redditclient.repository.RedditApiService
import io.reactivex.Observable

class RedditRepository(val redditApiService: RedditApiService) {
//todo
    fun letPosts(pagingConfig: PagingConfig = getDefaultPageConfig()): Observable<PagingData<PostData>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { PostsPagingSource(redditApiService) }
        ).observable
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    companion object{
        const val DEFAULT_PAGE_SIZE = 10
    }
}