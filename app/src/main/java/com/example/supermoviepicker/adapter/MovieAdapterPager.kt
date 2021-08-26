package com.example.supermoviepicker.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.supermoviepicker.model.Movie
import com.example.supermoviepicker.databinding.MovieViewItemBinding


class MovieAdapterPager(
    private val fragment: OnMovieClicker
) :
    PagingDataAdapter<Movie, MovieAdapterPager.MovieViewHolderPager>(MovieComparator) {

    interface OnMovieClicker {
        fun onMovieClick (movie: Movie?)
        fun loadImage(url:String?, view: ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolderPager {
        return MovieViewHolderPager(MovieViewItemBinding.inflate(LayoutInflater.from(parent.context)), fragment)
    }

    override fun onBindViewHolder(holder: MovieViewHolderPager, position: Int) {
        getItem(position)?.let {
            fragment.loadImage(getItem(position)!!.posterPath, holder.binding.imageMovie)
            holder.bind(getItem(position)!!)
        }
    }

    class MovieViewHolderPager(val binding: MovieViewItemBinding, val fragment: OnMovieClicker) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.textView.text = movie.originalTitle
            binding.root.setOnClickListener {
                fragment.onMovieClick(movie)
            }
        }
    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.originalTitle == newItem.originalTitle
        }
    }

}

