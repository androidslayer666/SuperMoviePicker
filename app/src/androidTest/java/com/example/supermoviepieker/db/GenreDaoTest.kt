package com.example.supermoviepieker.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.supermoviepieker.model.Genre
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class GenreDAOTest {

    private lateinit var database: GenreDatabase
    private lateinit var dao: GenreDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GenreDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.genreDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertGenre() = runBlocking {
        val genre = Genre(1,"Horror")
        dao.insertGenres(listOf(genre))
        val listOfGenres = dao.getAll()
        assert(listOfGenres.contains(genre))}
}