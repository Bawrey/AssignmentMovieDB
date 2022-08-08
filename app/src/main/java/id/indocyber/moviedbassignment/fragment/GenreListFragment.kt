package id.indocyber.moviedbassignment.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.AppResponseError
import id.indocyber.common.AppResponseLoading
import id.indocyber.common.AppResponseSuccess
import id.indocyber.common.base.BaseFragment
import id.indocyber.moviedbassignment.R
import id.indocyber.moviedbassignment.adapter.GenreItemAdapter
import id.indocyber.moviedbassignment.adapter.GenreItemDetailsLookup
import id.indocyber.moviedbassignment.adapter.GenreItemKeyProvider
import id.indocyber.moviedbassignment.databinding.LayoutGenreListBinding
import id.indocyber.moviedbassignment.view_model.GenreListViewModel

@AndroidEntryPoint
class GenreListFragment : BaseFragment<LayoutGenreListBinding, GenreListViewModel>() {
    override val viewModel: GenreListViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_genre_list
    private val adapter = GenreItemAdapter {
        viewModel.selection?.isSelected(it) ?: false
    }

    override fun bindingExt(binding: LayoutGenreListBinding) {
        super.bindingExt(binding)
        binding.recyclerViewGenreList.adapter = adapter
        viewModel.genreListData.observe(this) {
            when (it) {
                is AppResponseSuccess -> {
                    adapter.data.submitList(it.data)
                    binding.recyclerViewGenreList.visibility = View.VISIBLE
                    binding.loadingBar.visibility = View.GONE
                    binding.buttonNext.visibility = View.VISIBLE
                    binding.buttonRetry.visibility = View.GONE
                }
                is AppResponseError -> {
                    binding.recyclerViewGenreList.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                    binding.buttonNext.visibility = View.GONE
                    binding.buttonRetry.visibility = View.VISIBLE
                    alertDialogBuilder("Error",it.e?.message.toString())
                }
                is AppResponseLoading -> {
                    binding.recyclerViewGenreList.visibility = View.GONE
                    binding.buttonNext.visibility = View.GONE
                    binding.buttonRetry.visibility = View.GONE
                    binding.loadingBar.visibility = View.VISIBLE
                }
            }
        }
        viewModel.selection = viewModel.selection?.let {
            createTracker().apply {
                it.selection.forEach {
                    this.select(it)
                }
            }
        } ?: kotlin.run {
            createTracker()
        }
        binding.buttonNext.setOnClickListener {
            viewModel.navigateToMovieList()
        }
        binding.buttonRetry.setOnClickListener {
            viewModel.getGenreListData()
        }
    }

    private fun createTracker() = SelectionTracker.Builder(
        this@GenreListFragment::class.java.name,
        binding.recyclerViewGenreList,
        GenreItemKeyProvider(adapter),
        GenreItemDetailsLookup(binding.recyclerViewGenreList),
        StorageStrategy.createLongStorage()
    ).withOnItemActivatedListener { itemDetails, _ ->
        itemDetails.selectionKey?.let {
            viewModel.selection?.select(it)
        }
        true
    }.withSelectionPredicate(SelectionPredicates.createSelectAnything())
        .build()
}