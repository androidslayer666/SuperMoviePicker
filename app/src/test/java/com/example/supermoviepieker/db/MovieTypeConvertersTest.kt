package com.example.supermoviepieker.db

import com.google.common.truth.Truth.assertThat
import org.junit.Test



class MovieTypeConvertersTest {

    private val listOfGenreIds = listOf(1,2,3)
    val stringOfGenreIds = "1,2,3"


    @Test
    fun fromList() {
        val stringFromList = MovieTypeConverters().fromList(listOfGenreIds)
        assertThat(stringFromList).isNotEmpty()
    }

    @Test
    fun toList() {
        val listFromString = MovieTypeConverters().toList(stringOfGenreIds)
        assertThat(listFromString).isEqualTo(listOfGenreIds)
    }
}