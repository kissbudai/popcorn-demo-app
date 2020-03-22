package com.demo.popcornapp.feature.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.popcornapp.MovieItemBinding
import com.demo.popcornapp.R

class MovieAdapter : ListAdapter<MovieListItem, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    private var itemClickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = DataBindingUtil.inflate<MovieItemBinding>(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false)
        return MovieViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setup(getItem(position))
    }

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    class MovieViewHolder(private val binding: MovieItemBinding, private val clickListener: ((Int) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    clickListener?.invoke(adapterPosition)
                }
            }
        }

        fun setup(model: MovieListItem) {
            binding.movie = model

            binding.currentRating.text = buildSpannedString {
                bold { append(model.rating.toString()) }
                append(binding.root.context.getString(R.string.rating_from_ten))
                appendln()
                append(model.ratingCount.toString())
            }
        }
    }
}