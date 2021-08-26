package com.example.supermoviepicker.repository

import android.util.Log
import com.example.supermoviepicker.model.Genre
import com.example.supermoviepicker.model.Movie
import com.example.supermoviepicker.model.MovieDto
import com.example.supermoviepicker.viewModel.FilterMovies
import com.example.supermoviepicker.viewModel.SortingOptions
import java.util.*

fun movieTdoListToMovieList(list: List<MovieDto>): List<Movie> {
    val listMovies = mutableListOf<Movie>()
    for (movieDto in list) {
        val movie = Movie(id = Random().nextInt())
        movie.originalTitle = movieDto.originalTitle
        movie.adult = movieDto.adult
        movie.genreIds = movieDto.genreIds
        movie.id = movieDto.id
        movie.originalLanguage = movieDto.originalLanguage
        movie.overview = movieDto.overview
        movie.popularity = (movieDto.popularity?.times(10))?.toInt()
        movie.posterPath = movieDto.posterPath
        movie.releaseDate = movieDto.releaseDate
        if (movieDto.releaseDate?.substringBefore('-') != null
            && movieDto.releaseDate?.substringBefore('-') != ""
        ) {
            movie.year = movieDto.releaseDate?.substringBefore('-')?.toInt()
        }
        movie.video = movieDto.video
        movie.voteAverage = (movieDto.voteAverage?.times(10))?.toInt()
        movie.voteCount = movieDto.voteCount
        listMovies.add(movie)
    }
    return listMovies
}

fun queryBuilderOnFilter(
    listGenre: List<Genre>,
    filterMovie: FilterMovies?,
    sortingOptions: SortingOptions?
): String {

    var query = "SELECT * FROM movies "

    if (listGenre.isNotEmpty()) {
        for (i in listGenre) {
            if (i == listGenre.first()) query += " WHERE ("
            query += if (i == listGenre.last()) "genreIds LIKE '%' || ${i.id}|| '%')"
            else "genreIds LIKE '%' ||${i.id}|| '%' OR "
        }
    }

    if (filterMovie != null) {

        if (filterMovie.filterIfAdult.allowed == true) {
            query += " AND adult = 1"
        }

        if (filterMovie.rating.min != null) {
            val min = (filterMovie.rating.min!! * 10)
            query += " AND voteAverage >= $min"
        }
        if (filterMovie.rating.max != null) {
            val max = (filterMovie.rating.max!! * 10)
            query += " AND voteAverage < $max"
        }

        if (filterMovie.years.min != null) {
            val min = (filterMovie.years.min!!)
            query += " AND year >= $min"
        }
        if (filterMovie.years.max != null) {
            val max = (filterMovie.years.max!!)
            query += " AND year <= $max"
        }

        if (filterMovie.minReview.min!= null) {
            val min = (filterMovie.minReview.min!!)
            query += " AND voteCount >= $min"
        }
    }

    if (sortingOptions != null) {
        query += when (sortingOptions) {
            SortingOptions.RATINGDESC -> " ORDER BY voteAverage DESC"
            SortingOptions.RATINGASC -> " ORDER BY voteAverage ASC"
            SortingOptions.YEARDESC -> " ORDER BY year DESC"
            SortingOptions.YEARASC -> " ORDER BY year ASC"
        }
    }
    Log.d("queryBuilderOnFilter", query)
    return query
}
