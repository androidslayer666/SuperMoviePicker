package com.example.supermoviepicker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supermoviepicker.adapter.GenreAdapter
import com.example.supermoviepicker.model.Genre
import com.example.supermoviepicker.utils.MySharedPreferencesWrapper
import com.example.supermoviepicker.viewModel.GenreViewModel
import com.example.supermoviepicker.R
import com.example.supermoviepicker.databinding.GenreFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GenreFragment : Fragment(R.layout.genre_fragment) {

    @Inject
    lateinit var prefs: MySharedPreferencesWrapper

    private val TAG = "StartFragment"
    val viewModel : GenreViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        val binding = GenreFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerViewGenres.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewGenres.adapter = GenreAdapter() { genre -> onGenreClick(genre!!) }
        return binding.root
    }

    private fun onGenreClick(genre : Genre) {
        viewModel.toggleLikeGenre(genre)
    }
}