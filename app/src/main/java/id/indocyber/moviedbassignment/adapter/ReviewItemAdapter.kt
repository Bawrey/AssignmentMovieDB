package id.indocyber.moviedbassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.movie_review.Review
import id.indocyber.moviedbassignment.R
import id.indocyber.moviedbassignment.databinding.ItemMovieReviewBinding

class ReviewItemAdapter : PagingDataAdapter<Review, ReviewItemViewHolder>(differData) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
        return ReviewItemViewHolder(
            ItemMovieReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        val rowData = getItem(position)
        holder.binding.data = rowData
        holder.binding.cardMovieReview.animation = AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.animation_recycle_view)
    }

    companion object {
        val differData = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class ReviewItemViewHolder(val binding: ItemMovieReviewBinding) :
    RecyclerView.ViewHolder(binding.root)
