package com.example.supermoviepieker.repository

import com.example.supermoviepieker.model.Genre
import com.example.supermoviepieker.utils.Resource


class FakeGenreRepositoryImpl(): GenreRepository {

    override suspend fun getGenres(): Resource<List<Genre>> {
        return Resource.success(listOf())
    }

    override suspend fun populateDB() {
    }

    fun getEmptyList(): Resource<List<Genre>> {
        return Resource.success(listOf())
    }
}
