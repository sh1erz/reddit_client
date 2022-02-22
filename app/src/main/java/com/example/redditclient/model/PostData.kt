package com.example.redditclient.model

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("title") val title : String,
    @SerializedName("author_fullname") val author : String,
    @SerializedName("subreddit") val subreddit : String,
    @SerializedName("created") val created : Long,
    @SerializedName("thumbnail") val thumbnail : String?,
    @SerializedName("num_comments") val comments : Int,
    @SerializedName("score") val score : Int,
    @SerializedName("permalink") val url : String,
) : Data()
