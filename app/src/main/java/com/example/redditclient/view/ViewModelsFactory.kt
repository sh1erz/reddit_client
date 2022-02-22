package com.example.redditclient.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.redditclient.data.RedditRepository
import com.example.redditclient.repository.RedditApiService
import com.example.redditclient.view.main.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelsFactory : ViewModelProvider.Factory {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.reddit.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RedditApiService::class.java)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                repository = RedditRepository(
                    redditApiService = retrofit
                )
            )
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T

}