package com.example.supermoviepicker.db

import androidx.room.TypeConverter

class MovieTypeConverters {

    @TypeConverter
    fun fromList(list: List<Int>) : String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toList(str: String): List<Int> {
        return str.split(",").filterNot { it == "" }.map {  it.toInt()  }
    }
}