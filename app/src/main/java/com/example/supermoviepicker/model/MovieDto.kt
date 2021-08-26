package com.example.supermoviepicker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class MoviesPage(
    @SerializedName ("page")
    var pageNumber : Int? = null,
    @SerializedName ("results")
    var results : List<MovieDto>? = null,
    @SerializedName ("total_pages")
    val totalPages: Int? = null,
    @SerializedName ("total_results")
    val totalResults: Int? = null
)


@Entity(tableName ="movies")
data class MovieDto(
    @SerializedName ("poster_path")
    var posterPath : String? = null,
    @SerializedName ("adult")
    var adult : Boolean? = null,
    @SerializedName ("overview")
    var overview : String? = null,
    @SerializedName ("release_date")
    var releaseDate : String? = null,
    @SerializedName ("genre_ids")
    var genreIds : List<Int>? = null,
    @PrimaryKey
    @SerializedName ("id")
    var id : Int,
    @SerializedName ("original_title")
    var originalTitle : String? = null,
    @SerializedName ("original_language")
    var originalLanguage: String? = null,
    @SerializedName ("popularity")
    var popularity : Double? = null,
    @SerializedName ("vote_count")
    var voteCount : Int? = null,
    @SerializedName ("video")
    var video : Boolean? = null,
    @SerializedName ("vote_average")
    var voteAverage : Double? = null,
    var liked: Boolean? = false
)