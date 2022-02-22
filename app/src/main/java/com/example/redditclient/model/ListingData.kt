package com.example.redditclient.model

import com.google.gson.annotations.SerializedName

data class ListingData(
    @SerializedName("after") val after: String?,
    @SerializedName("children") val children: List<Thing<PostData>>,
    @SerializedName("before") val before: String?
) : Data()
