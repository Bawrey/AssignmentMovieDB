package id.indocyber.moviedbassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.moviedbassignment.databinding.ItemLoadStateBinding

class PagingLoadStateAdapter(val onClick: () -> Unit) :
    LoadStateAdapter<PagingLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.NotLoading -> {
                holder.binding.loadingBar.visibility = View.GONE
                holder.binding.buttonRetry.visibility = View.GONE
            }
            is LoadState.Loading -> {
                holder.binding.loadingBar.visibility = View.VISIBLE
                holder.binding.buttonRetry.visibility = View.GONE

            }
            is LoadState.Error -> {
                holder.binding.loadingBar.visibility = View.GONE
                holder.binding.buttonRetry.visibility = View.VISIBLE

            }
        }
        holder.binding.buttonRetry.setOnClickListener {
            onClick()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder {
        return PagingLoadStateViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class PagingLoadStateViewHolder(val binding: ItemLoadStateBinding) :
    RecyclerView.ViewHolder(binding.root)
