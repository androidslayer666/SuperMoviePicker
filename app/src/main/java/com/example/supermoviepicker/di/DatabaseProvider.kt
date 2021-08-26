package com.example.supermoviepicker.di

import android.content.Context
import androidx.room.Room
import com.example.supermoviepicker.db.GenreDAO
import com.example.supermoviepicker.db.GenreDatabase
import com.example.supermoviepicker.db.MovieDAO
import com.example.supermoviepicker.db.MovieDatabase
import com.example.supermoviepicker.network.Api
import com.example.supermoviepicker.repository.GenreRepository
import com.example.supermoviepicker.repository.GenreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module

class DatabaseProvider {


    @Provides
    @Singleton
    fun provideRepository(genreDAO: GenreDAO, api : Api) : GenreRepository {
        return GenreRepositoryImpl(
            genreDAO,
            api
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): GenreDatabase {
        return Room.databaseBuilder(
            appContext,
            GenreDatabase::class.java,
            "genres"
        ).build()
    }

    @Provides
    fun provideGenreDao(database: GenreDatabase): GenreDAO {
        return database.genreDAO()
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movies"
        ).build()
    }

    @Provides
    fun provideMovieDao(databaseMovie: MovieDatabase): MovieDAO {
        return databaseMovie.movieDao()
    }

}