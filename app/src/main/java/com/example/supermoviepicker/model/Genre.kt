package com.example.supermoviepicker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GenresList(
        @SerializedName("genres")
        var listGenre: List<Genre>? = null
)

@Entity(tableName = "genres")
data class Genre(
        @PrimaryKey
        var id: Int? = null,
        var name: String? = null,
        var liked: Boolean = false
)