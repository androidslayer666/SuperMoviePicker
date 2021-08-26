package com.example.supermoviepicker.db

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

import androidx.room.RawQuery
import com.example.supermoviepicker.model.Movie
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDAO {

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * FROM movies")
    fun getAllPaged(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movies LIMIT :limit")
    fun getAllLimited(limit : Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movieList: List<Movie>)

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Query("SELECT COUNT(id) FROM movies")
    fun getCount(): Int

    @Query("SELECT * FROM movies WHERE genreIds LIKE :genreId LIMIT :limit"
    )
    fun getMovieWithGenre(genreId: String, limit : Int): Flow<List<Movie>>

    @RawQuery(observedEntities = [Movie::class])
    fun getMovieWithSeveralGenres(query: SupportSQLiteQuery?): List<Movie>

    @RawQuery(observedEntities = [Movie::class])
    fun getMovieWithSeveralGenresFlow(query: SupportSQLiteQuery?): Flow<List<Movie>>

    @RawQuery(observedEntities = [Movie::class])
    fun getMovieWithSeveralGenresPager(query: SupportSQLiteQuery?): PagingSource<Int, Movie>

    @Query("SELECT * FROM movies WHERE id = :movieId"
    )
    fun getMovieWithId(movieId: Int): Flow<Movie?>

    @Query("SELECT * FROM movies WHERE liked = 1"
    )
    fun getAllLikedMovies(): PagingSource<Int, Movie>

    @Update
    suspend fun toggleMovieLike(movie: Movie)

//    @Query("SELECT id FROM movies")
//    suspend fun getAllIds(): List<Int>?

    @Query("SELECT  * FROM movies ORDER BY RANDOM() LIMIT 1")
    fun getRandomMovie() : Movie
}