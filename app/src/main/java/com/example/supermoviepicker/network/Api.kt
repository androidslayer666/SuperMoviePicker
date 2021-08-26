package com.example.supermoviepicker.network

import com.example.supermoviepicker.BuildConfig
import com.example.supermoviepicker.Constants.BASE_URL
import com.example.supermoviepicker.model.GenresList
import com.example.supermoviepicker.model.MoviesPage
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap



const val key = BuildConfig.API_KEY

interface Api {

    @GET("genre/movie/list?api_key=$key")
    suspend fun getGenres(): Response<GenresList>

    @GET("movie/popular?api_key=$key")
    suspend fun getPopularMovies(@QueryMap param :Map<String, Int>): MoviesPage

    companion object {

        fun create(): Api {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(Api::class.java)
        }
    }
}