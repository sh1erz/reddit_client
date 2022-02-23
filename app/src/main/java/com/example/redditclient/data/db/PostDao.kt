package com.example.redditclient.data.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.redditclient.model.PostData
import retrofit2.http.DELETE

@Dao
interface PostDao {
    @Query("SELECT * FROM postdata")
    fun getAllPosts() : PagingSource<Int,PostData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPosts(posts : List<PostData>)

    @Query("DELETE FROM postdata" )
    fun deleteAllPosts()

}