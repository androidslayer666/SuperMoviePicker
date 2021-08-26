package com.example.supermoviepicker.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.supermoviepicker.Constants.IMAGE_DATABASE_LINK
import com.example.supermoviepicker.adapter.GenreAdapter
import com.example.supermoviepicker.model.Genre
import com.example.supermoviepicker.model.Movie


const val TAG = "bindRecyclerView"

@BindingAdapter("listGenreData")
fun bindRVGenre (recyclerView: RecyclerView, listMovies: List<Genre>?) {
    val adapterMovie = recyclerView.adapter as GenreAdapter
    adapterMovie.submitList(listMovies)
}

@BindingAdapter("loadingErrorVisibility")
fun loadingErrorVisibility (textView: TextView, listMovies: Resource<List<Movie>>?) {
    when(listMovies?.status) {
        Status.SUCCESS -> textView.visibility = View.GONE
        Status.LOADING -> {
            textView.visibility = View.VISIBLE
            textView.text = "listMovies.message"
        }
        Status.ERROR -> {
            textView.visibility = View.VISIBLE
            textView.text = listMovies.message
        }
    }
}


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        imgView.load(IMAGE_DATABASE_LINK + imgUrl)
    }
}


