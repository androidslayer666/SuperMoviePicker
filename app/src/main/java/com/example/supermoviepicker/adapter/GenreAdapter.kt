package com.example.supermoviepicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.supermoviepicker.model.Genre
import com.example.supermoviepicker.R
import com.example.supermoviepicker.databinding.GenreViewItemBinding

class GenreAdapter(private val onSelect: (Genre?) -> Unit) :
    ListAdapter<Genre, GenreAdapter.GenreViewHolder>(DiffCallback) {

    private val TAG = "GenreAdapter"

    object DiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.name == newItem.name && oldItem.liked == newItem.liked
        }
    }

    class GenreViewHolder(
        private var binding: GenreViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre, onSelect: (Genre?) -> Unit) {
            binding.genre = genre
            binding.nameGenre.isActivated = genre.liked
            binding.imageViewLike.setImageResource(
                if (!genre.liked) R.drawable.heart_blank else R.drawable.heart_filled)
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onSelect(genre)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(GenreViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre, onSelect)
    }
}

