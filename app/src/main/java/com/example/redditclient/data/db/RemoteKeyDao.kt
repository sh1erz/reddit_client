package com.example.redditclient.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateKey(remoteKey: RemoteKey)

    @Query("SELECT * FROM remotekey")
    suspend fun remoteKey(): RemoteKey

    @Query("DELETE FROM remotekey")
    suspend fun deleteRemoteKey()
}