package com.example.supermoviepicker.viewModel

import androidx.lifecycle.*
import com.example.supermoviepicker.model.Movie
import com.example.supermoviepicker.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    state: SavedStateHandle
) : ViewModel() {

    private var _randomMovie = MutableLiveData<Movie>()
    val randomMovie : LiveData<Movie> = _randomMovie


    var chosenMovie: LiveData<Movie?> = liveData {  }

    fun getRandomMovieVM() {
        viewModelScope.launch(IO) {
            val movie = repository.getRandomMovie()
            withContext(Main) {_randomMovie.value = movie}
        }
    }

    fun toggleLikeMovie(movie: Movie) {
        viewModelScope.launch(IO) {
            if (movie.liked == false || movie.liked == null) {
                movie.liked = true
                repository.toggleMovieLike(movie)
            } else {
                movie.liked = false
                repository.toggleMovieLike(movie)
            }
        }
    }

    fun setMovie (id: Int) {
        chosenMovie = repository.getMovieWithId(id).asLiveData()
    }
}