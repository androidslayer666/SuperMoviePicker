package com.example.supermoviepicker.viewModel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.supermoviepicker.Constants.PAGING_PAGE_SIZE
import com.example.supermoviepicker.db.MovieDAO
import com.example.supermoviepicker.model.Movie
import com.example.supermoviepicker.repository.GenreRepository
import com.example.supermoviepicker.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val genreRepository: GenreRepository,
    dao: MovieDAO
) : ViewModel() {

    private val TAG = "MovieViewModel"

    var filterMovies = FilterMovies()
        private set

    var sorting: SortingOptions? = null
        private set

    val likedMovies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = PAGING_PAGE_SIZE)) {
        dao.getAllLikedMovies()
    }.flow
        .cachedIn(viewModelScope)

    fun getMovies():  Flow<PagingData<Movie>>{
        return genreRepository.getLikedGenreFlow()
            .transform { listGenre ->
                if(listGenre.isNotEmpty()) {
                    Pager(PagingConfig(pageSize = PAGING_PAGE_SIZE)) {
                        repository.getMovieWithSeveralGenresPager(listGenre, filterMovies, sorting)
                    }.flow.collect { emit(it) }
                }
            }.cachedIn(viewModelScope)
    }

    fun setSortingOption(sortingOption: SortingOptions) {
        Log.d(TAG, sortingOption.toString())
        sorting = sortingOption
    }

    fun setFilter(value: Int = 0, filterOption: FilterOption, isChecked: Boolean = false) {
        when (filterOption) {
            FilterOption.ADULT -> filterMovies.filterIfAdult.allowed = isChecked
            FilterOption.MINRATING ->  filterMovies.rating.min = value
            FilterOption.MAXRATING ->  filterMovies.rating.max = value
            FilterOption.MINYEAR -> filterMovies.years.min = value
            FilterOption.MAXYEAR -> filterMovies.years.max = value
            FilterOption.MINREVIEW -> filterMovies.minReview.min = value
        }
    }

    fun clearFilter() {
        filterMovies = FilterMovies()
    }
}

