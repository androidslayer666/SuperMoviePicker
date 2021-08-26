package com.example.supermoviepicker.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.supermoviepicker.R
import com.example.supermoviepicker.adapter.MovieAdapterPager
import com.example.supermoviepicker.model.Movie
import com.example.supermoviepicker.ui.loadImageGlide
import com.example.supermoviepicker.ui.parts.DetailDialog

open class BaseFragment(id: Int) : Fragment(id), MovieAdapterPager.OnMovieClicker  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
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