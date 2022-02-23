package com.example.redditclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.redditclient.model.PostData

@Database(entities = [PostData::class, RemoteKey::class], version = 1)
abstract class PostDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}