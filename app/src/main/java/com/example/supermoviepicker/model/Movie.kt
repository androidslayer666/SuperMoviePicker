package com.example.supermoviepicker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movies")
data class Movie(
    var posterPath: String? = null,
    var adult: Boolean? = null,
    var overview: String? = null,
    var releaseDate: String? = null,
    var year: Int? = null,
    var genreIds: List<Int>? = null,
    @PrimaryKey
    var id: Int,
    var originalTitle: String? = null,
    var originalLanguage: String? = null,
    var popularity: Int? = null,
    var voteCount: Int? = null,
    var video: Boolean? = null,
    var voteAverage: Int? = null,
    var liked: Boolean? = false
)