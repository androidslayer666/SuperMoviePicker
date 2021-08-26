package com.example.supermoviepicker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.supermoviepicker.model.Movie


@Database(entities = arrayOf(Movie::class), version = 1)
@TypeConverters(MovieTypeConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDAO

    companion object{
        @Volatile private var instance: MovieDatabase? = null

        fun getInstance (context: Context) : MovieDatabase {
            return instance ?: synchronized(this) {
                MovieDatabase.instance
                    ?: MovieDatabase.buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MovieDatabase {
            return Room.databaseBuilder(context, MovieDatabase::class.java, "movies").build()
        }
    }
}