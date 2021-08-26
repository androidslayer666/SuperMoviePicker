package com.example.supermoviepicker

import android.app.Application
import android.util.Log
import com.example.supermoviepicker.db.GenreDAO
import com.example.supermoviepicker.db.MovieDAO
import com.example.supermoviepicker.repository.GenreRepository

import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import javax.inject.Inject
import com.example.supermoviepicker.repository.MovieRepository as MovieRepository1


@HiltAndroidApp
class MovieApplication : Application() {
    @Inject
    lateinit var movieRepository: MovieRepository1

    @Inject
    lateinit var movieDAO: MovieDAO

    @Inject
    lateinit var genreRepository: GenreRepository

    @Inject
    lateinit var genreDAO: GenreDAO

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch() {
            if (genreDAO.getCount() == 0) {
                genreRepository.populateDB()
                Log.d("MovieApplication", "genres are populated")
            }

            if (movieDAO.getCount() < 1000) {
                movieRepository.populateDB()
                Log.d("MovieApplication", "movies are populated")
            }

        }
    }
}