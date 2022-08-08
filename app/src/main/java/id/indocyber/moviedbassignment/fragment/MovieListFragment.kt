package id.indocyber.moviedbassignment.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.moviedbassignment.R
import id.indocyber.moviedbassignment.adapter.MovieItemAdapter
import id.indocyber.moviedbassignment.adapter.PagingLoadStateAdapter
import id.indocyber.moviedbassignment.databinding.LayoutMovieListBinding
import id.indocyber.moviedbassignment.view_model.MovieListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : BaseFragment<LayoutMovieListBinding, MovieListViewModel>() {
    override val viewModel: MovieListViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_movie_list
    private val args by navArgs<MovieListFragmentArgs>()
    private val adapter = MovieItemAdapter {
        viewModel.navigateToMovieDetail(it)
    }
    private val stateAdapter = PagingLoadStateAdapter {
        onRetry()
    }

    override fun bindingExt(binding: LayoutMovieListBinding) {
        super.bindingExt(binding)
        binding.recyclerViewMovieList.adapter = adapter.withLoadStateFooter(stateAdapter)
        viewModel.getMovieListData(args.genreIds.orEmpty())
        viewModel.movieListData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
        binding.buttonRetry.setOnClickListener {
            onRetry()
        }
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.recyclerViewMovieList.visibility = View.GONE
                binding.buttonRetry.visibility = View.VISIBLE
                binding.loadingBar.visibility = View.GONE
                binding.textNoMovie.visibility = View.VISIBLE
            } else if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                binding.recyclerViewMovieList.visibility = View.GONE
                binding.buttonRetry.visibility = View.GONE
                binding.loadingBar.visibility = View.VISIBLE
                binding.textNoMovie.visibility = View.GONE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount != 0) {
                binding.recyclerViewMovieList.visibility = View.VISIBLE
                binding.buttonRetry.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
                binding.textNoMovie.visibility = View.GONE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                binding.recyclerViewMovieList.visibility = View.VISIBLE
                binding.buttonRetry.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
                binding.textNoMovie.visibility = View.VISIBLE
            }
        }
    }

    private fun onRetry() {
        adapter.retry()
    }
}