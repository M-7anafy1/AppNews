package com.example.registration.database

import androidx.lifecycle.LiveData
import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun upsert(favorite: FavoriteDB)

    @Delete
    suspend  fun delete(favorite: FavoriteDB)

    @Query("SELECT * FROM favorites")
     fun getAllArticles(): Flow<List<FavoriteDB>>

    @Query("SELECT url FROM favorites")
    fun getFavoriteUrls(): Flow<List<String>>
}