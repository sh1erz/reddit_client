package com.example.redditclient.repository

import com.example.redditclient.model.ListingData
import com.example.redditclient.model.Thing
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApiService {
    @GET("r/all/top.json")
    suspend fun getPosts(
        @Query("after") after: String?,
        @Query("limit") limit: Int = 10
    ) : Thing<ListingData>
}