package com.example.supermoviepicker.db

import androidx.room.*
import com.example.supermoviepicker.model.Genre

import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDAO {

    @Query("SELECT * FROM genres")
    suspend fun getAll(): List<Genre>

    @Query("SELECT * FROM genres")
    fun getAllFlow(): Flow<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genreList: List<Genre>)

    @Query("SELECT COUNT(id) FROM genres")
    suspend fun getCount(): Int

    @Update
    suspend fun toggleLikeGenre(genre: Genre)

    @Query("SELECT * FROM genres WHERE liked = 1")
    fun getLikedGenre(): List<Genre>

    @Query("SELECT * FROM genres WHERE liked = 1")
    fun getLikedGenreFlow(): Flow<List<Genre>>

}