package com.example.supermoviepicker.repository


import com.example.supermoviepicker.model.Genre
import com.example.supermoviepicker.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GenreRepository {

    suspend fun getGenres(): Resource<List<Genre>>

    fun getGenresFlow() :Flow<List<Genre>>

    suspend fun populateDB()
    suspend fun toggleLikeGenre(genre: Genre)
    fun getLikedGenre(): List<Genre>
    fun getLikedGenreFlow(): Flow<List<Genre>>

}