package com.example.redditclient.view.main

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.redditclient.repository.RedditRepository
import com.example.redditclient.model.PostData
import io.reactivex.Observable

@ExperimentalPagingApi
class MainViewModel(val repository: RedditRepository) : ViewModel() {

    val posts = repository.getPosts()

}