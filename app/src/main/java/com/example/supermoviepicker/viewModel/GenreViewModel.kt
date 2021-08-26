package com.example.supermoviepicker.viewModel

import androidx.lifecycle.*
import com.example.supermoviepicker.model.Genre
import com.example.supermoviepicker.repository.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GenreViewModel @Inject constructor(val repository: GenreRepository) : ViewModel() {

    private val TAG = "GenreViewModel"

    private var _listGenres = MutableLiveData<List<Genre>>()
    val listGenre: LiveData<List<Genre>> = _listGenres


    val listGenreFlow = repository.getGenresFlow().asLiveData()

    fun settingListGenreOnAMain(updatedListGenre: List<Genre>) {
        viewModelScope.launch(Main) {
            _listGenres.value = updatedListGenre
        }
    }

    fun toggleLikeGenre(genre: Genre) {
        viewModelScope.launch(IO) {
            repository.toggleLikeGenre(Genre(genre.id, genre.name, !genre.liked))
        }
    }
}
