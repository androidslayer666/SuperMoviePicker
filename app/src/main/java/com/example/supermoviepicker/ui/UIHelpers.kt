package com.example.supermoviepicker.ui

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.supermoviepicker.Constants
import com.example.supermoviepicker.R

fun loadImageGlide(url: String?, view: ImageView?, fragment: Fragment) {
    view?.let {
        Glide.with(fragment)
            .load(Constants.IMAGE_DATABASE_LINK + url)
            .override(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(view)
    }
}