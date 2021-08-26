package com.example.supermoviepicker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.supermoviepicker.model.Genre


@Database(entities = arrayOf(Genre::class), version = 1 )
abstract class GenreDatabase : RoomDatabase() {
    abstract fun genreDAO() : GenreDAO

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: GenreDatabase? = null

        fun getInstance(context: Context): GenreDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): GenreDatabase {
            return Room.databaseBuilder(context, GenreDatabase::class.java, "genres").build()
        }
    }


}