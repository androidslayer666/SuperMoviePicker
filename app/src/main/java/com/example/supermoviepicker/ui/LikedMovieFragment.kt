package com.example.supermoviepicker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.supermoviepicker.Constants.RECYCLERVIEW_ROW_NUMBER
import com.example.supermoviepicker.adapter.MovieAdapterPager
import com.example.supermoviepicker.model.Movie
import com.example.supermoviepicker.ui.parts.DetailDialog
import com.example.supermoviepicker.viewModel.MovieViewModel
import com.example.supermoviepicker.databinding.FragmentLikedMovieBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LikedMovieFragment : Fragment(), MovieAdapterPager.OnMovieClicker {

    val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val movieAdapter = MovieAdapterPager ( this)

        val binding = FragmentLikedMovieBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.recyclerViewLikedMovie.layoutManager = GridLayoutManager(activity, RECYCLERVIEW_ROW_NUMBER)
        binding.recyclerViewLikedMovie.adapter = movieAdapter

        lifecycleScope.launch() {
            viewModel.likedMovies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
        return binding.root
    }

    override fun onMovieClick(movie: Movie?) {
        movie?.let {
            DetailDialog(movie).show(parentFragmentManager, "Details")
        }
    }

    override fun loadImage(url: String?, view: ImageView) {
        loadImageGlide(url, view, this)
    }
}