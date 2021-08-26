package com.example.supermoviepicker.ui.parts

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.supermoviepicker.model.Movie
import com.example.supermoviepicker.viewModel.MovieDetailViewModel
import com.example.supermoviepicker.R
import com.example.supermoviepicker.databinding.FragmentMovieDetailBinding

class DetailDialog(val movie: Movie): DialogFragment() {

    val viewModel: MovieDetailViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { it ->
            val builder = AlertDialog.Builder(it)
            val binding: FragmentMovieDetailBinding = DataBindingUtil.inflate(
                LayoutInflater.from(
                    context
                ), R.layout.fragment_movie_detail, null, false
            )
            viewModel.setMovie(movie.id)
            Log.d("DetailDialog", "I'm here")
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
            binding.buttonLike.setOnClickListener {
                viewModel.toggleLikeMovie(movie)
            }

            dialog?.setCanceledOnTouchOutside(true);
            builder.setView(binding.root)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}