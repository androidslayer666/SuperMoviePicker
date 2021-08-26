package com.example.supermoviepicker.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.supermoviepicker.db.MovieDAO
import com.example.supermoviepicker.model.Genre
import com.example.supermoviepicker.model.Movie
import com.example.supermoviepicker.network.Api
import com.example.supermoviepicker.viewModel.FilterMovies
import com.example.supermoviepicker.viewModel.SortingOptions
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDao: MovieDAO,
    private val api: Api
) {

    private val TAG = "MovieRepository"

    fun getMovieWithId(movieId: Int): Flow<Movie?> = movieDao.getMovieWithId(movieId)

    fun getRandomMovie(): Movie = movieDao.getRandomMovie()


    suspend fun populateDB() {
        val numberOfPages = api.getPopularMovies(mapOf("page" to 1)).totalPages ?: 0
        Log.d(TAG, "Number of pages $numberOfPages")
        for (i in 1..numberOfPages) {
            Log.d(TAG, "Downloading movies on page $i")
            val movies = api.getPopularMovies(mapOf("page" to i)).results!!
            movieDao.insertMovies(movieTdoListToMovieList(movies))
        }
    }

    suspend fun toggleMovieLike(movie: Movie) {
        return movieDao.toggleMovieLike(movie)
    }

    //todo Check for empty list
    fun getMovieWithSeveralGenresPager(
        listGenre: List<Genre>,
        filterMovie: FilterMovies? = null,
        sortingOptions: SortingOptions? = null
    )
            : PagingSource<Int, Movie> {
        val querySQL = SimpleSQLiteQuery(queryBuilderOnFilter(listGenre, filterMovie, sortingOptions))
        return movieDao.getMovieWithSeveralGenresPager(querySQL)
    }
}
