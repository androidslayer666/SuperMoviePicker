package com.example.supermoviepicker.ui

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.supermoviepicker.Constants.RECYCLERVIEW_ROW_NUMBER
import com.example.supermoviepicker.R
import com.example.supermoviepicker.adapter.MovieAdapterPager
import com.example.supermoviepicker.ui.base.BaseFragment
import com.example.supermoviepicker.ui.parts.FilterSortingFragment
import com.example.supermoviepicker.viewModel.MovieViewModel
import com.example.supermoviepicker.databinding.MovieFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieFragment : BaseFragment(R.layout.movie_fragment), ListUpdater,
    MovieAdapterPager.OnMovieClicker {

    val viewModel: MovieViewModel by activityViewModels()

    private val movieAdapter = MovieAdapterPager( this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Creating a sort and filter fragment as a child fragment
        parentFragmentManager.beginTransaction()
            .replace(R.id.layout_for_filtering_and_sorting, FilterSortingFragment())
            .commit()

        val binding = MovieFragmentBinding.inflate(layoutInflater)
        binding.apply {
            lifecycleOwner = this@MovieFragment
            recyclerViewMovies.layoutManager = GridLayoutManager(activity, RECYCLERVIEW_ROW_NUMBER)
            recyclerViewMovies.adapter = movieAdapter
        }

        // Initial launch of paging
        reloadPagingWhenApplyFilter()

        return binding.root
    }

    // Initial launch of paging or reload with filtering or sorting
    private fun reloadPagingWhenApplyFilter() {
        lifecycleScope.launch() {
            viewModel.getMovies().collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    // Interface impl for children to update interface
    override fun updateList() {
        reloadPagingWhenApplyFilter()
    }
}