package com.example.redditclient.view.main

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.redditclient.data.RedditRepository
import com.example.redditclient.model.PostData
import io.reactivex.Observable

class MainViewModel(val repository: RedditRepository) : ViewModel() {

    fun fetchPostsObservable(): Observable<PagingData<PostData>> {
        return repository.letPosts()
    }
}