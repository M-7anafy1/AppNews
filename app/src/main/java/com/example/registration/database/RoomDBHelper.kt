package com.example.registration.database

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
@Database(entities = [FavoriteDB::class], version = 1, exportSchema = false)
abstract class RoomDBHelper: RoomDatabase() {

    abstract val dao: NewsDao

    companion object{
        @Volatile
        private var INSTANCE: RoomDBHelper? = null

        fun getInstance(c: Context): RoomDBHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(c, RoomDBHelper::class.java, "news_db")
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}