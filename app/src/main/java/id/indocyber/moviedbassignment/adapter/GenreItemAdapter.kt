package id.indocyber.moviedbassignment.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.genre_list.Genre
import id.indocyber.moviedbassignment.R
import id.indocyber.moviedbassignment.databinding.ItemGenreListBinding

class GenreItemAdapter(val isSelected: (Long) -> Boolean) :
    RecyclerView.Adapter<GenreItemViewHolder>() {

    init {
        setHasStableIds(true)
    }

    val data = AsyncListDiffer(this, differData)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreItemViewHolder {
        return GenreItemViewHolder(
            ItemGenreListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), data
        )
    }

    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) {
        val rowData = data.currentList[position]
        holder.binding.data = rowData
        holder.binding.isSelected = isSelected(rowData.id.toLong())
        holder.binding.cardGenreItem.animation = AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.animation_recycle_view)
    }

    override fun getItemCount(): Int = data.currentList.size

    override fun getItemId(position: Int): Long = data.currentList[position].id.toLong()

    companion object {
        val differData = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class GenreItemViewHolder(val binding: ItemGenreListBinding, val data: AsyncListDiffer<Genre>) :
    RecyclerView.ViewHolder(binding.root) {
    fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {
        override fun getPosition(): Int = absoluteAdapterPosition

        override fun getSelectionKey(): Long =
            data.currentList[absoluteAdapterPosition].id.toLong()
    }
}

class GenreItemKeyProvider(private val genreItemAdapter: GenreItemAdapter) :
    ItemKeyProvider<Long>(SCOPE_CACHED) {
    override fun getKey(position: Int): Long =
        genreItemAdapter.data.currentList[position].id.toLong()

    override fun getPosition(key: Long): Int =
        genreItemAdapter.data.currentList.indexOfFirst { it.id.toLong() == key }
}

class GenreItemDetailsLookup(val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? =
        recyclerView.findChildViewUnder(e.x, e.y)?.let {
            (recyclerView.getChildViewHolder(it) as GenreItemViewHolder).getItemDetails()
        }
}