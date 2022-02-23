package com.example.redditclient.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class PostData(
    @PrimaryKey
    @SerializedName("name") val name : String,
    @SerializedName("title") val title : String,
    @SerializedName("author") val author : String,
    @SerializedName("subreddit") val subreddit : String,
    @SerializedName("created") val created : Long,
    @SerializedName("thumbnail") val thumbnailUrl : String?,
    @SerializedName("num_comments") val comments : Int,
    @SerializedName("score") val score : Int,
    @SerializedName("permalink") val url : String,
) : Data()
