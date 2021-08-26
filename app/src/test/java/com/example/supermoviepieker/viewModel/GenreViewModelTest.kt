package com.example.supermoviepieker.viewModel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.supermoviepieker.MainCoroutineRule
import com.example.supermoviepieker.observeForTesting
import com.example.supermoviepieker.repository.FakeGenreRepositoryImpl
import com.example.supermoviepieker.utils.Status
import com.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import retrofit2.Response

class GenreViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GenreViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val repositoryImpl = FakeGenreRepositoryImpl()


    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = GenreViewModel(repositoryImpl)
    }

    @Test
    fun `what if we pass blank list to the function`() {
        viewModel.settingListGenreOnAMain(repositoryImpl.getEmptyList())
        assertThat(viewModel.listGenre.getOrAwaitValue().status).isEqualTo(Status.LOADING)
        Thread.sleep(10)
        assertThat(viewModel.listGenre.getOrAwaitValue().status).isEqualTo(Status.SUCCESS)
    }
}