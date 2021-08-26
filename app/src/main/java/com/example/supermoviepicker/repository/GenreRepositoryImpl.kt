package com.example.supermoviepicker.repository

import com.example.supermoviepicker.db.GenreDAO
import com.example.supermoviepicker.model.Genre
import com.example.supermoviepicker.network.Api
import com.example.supermoviepicker.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreRepositoryImpl
@Inject constructor(
    val genreDao: GenreDAO, val api: Api
) : GenreRepository {

    private val TAG = "GenreRepositoryImpl"

    override suspend fun toggleLikeGenre(genre: Genre) {
        genreDao.toggleLikeGenre(genre)
    }

    override suspend fun getGenres(): Resource<List<Genre>> {
        if (genreDao.getCount() == 0) {
            populateDB()
        }
        return Resource.success(genreDao.getAll())
    }

    //TODO make suspend populate within flow
    override fun getGenresFlow() :Flow<List<Genre>> {
        return genreDao.getAllFlow()
    }

    override suspend fun populateDB() {
        val response = api.getGenres()
        if (response.isSuccessful) {
            response.body()?.let {
                genreDao.insertGenres(it.listGenre!!)
            }
        }
    }

    override fun getLikedGenre(): List<Genre> {
        return genreDao.getLikedGenre()
    }

    override fun getLikedGenreFlow(): Flow<List<Genre>> {
        return genreDao.getLikedGenreFlow()
    }
}