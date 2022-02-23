package com.example.redditclient.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//todo with sharedPrefs?
@Entity
data class RemoteKey(
    @PrimaryKey
    val id : Int = 0,
    val nextKey: String?
)
