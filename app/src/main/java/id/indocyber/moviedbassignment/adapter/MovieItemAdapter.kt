package id.indocyber.moviedbassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.movie_list.Movie
import id.indocyber.moviedbassignment.R
import id.indocyber.moviedbassignment.databinding.ItemMovieListBinding

class MovieItemAdapter(val onClick: (String) -> Unit) :
    PagingDataAdapter<Movie, MovieItemViewHolder>(differData) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            ItemMovieListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val rowData = getItem(position)
        holder.binding.data = rowData
        holder.binding.cardMovieItem.setOnClickListener {
            onClick(rowData?.id.toString())
        }
        holder.binding.cardMovieItem.animation = AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.animation_recycle_view)
    }

    companion object {
        val differData = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class MovieItemViewHolder(val binding: ItemMovieListBinding) :
    RecyclerView.ViewHolder(binding.root)
