package com.example.redditclient.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.example.redditclient.data.db.PostDb
import com.example.redditclient.repository.RedditRepository
import com.example.redditclient.repository.RedditApiService
import com.example.redditclient.view.main.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalPagingApi
class ViewModelsFactory(context : Context) : ViewModelProvider.Factory {
    private val retrofit = Retrofit.Builder()
        .baseUrl(REDDIT_DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RedditApiService::class.java)

    private val postDb = Room.databaseBuilder(
        context,
        PostDb::class.java, "post-db"
    ).build()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                repository = RedditRepository(
                    redditApiService = retrofit,
                    postDb = postDb
                )
            )
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T

    companion object{
        const val REDDIT_DOMAIN = "https://www.reddit.com/"
    }
}