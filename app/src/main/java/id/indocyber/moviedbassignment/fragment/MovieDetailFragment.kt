package id.indocyber.moviedbassignment.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.AppResponseError
import id.indocyber.common.AppResponseLoading
import id.indocyber.common.AppResponseSuccess
import id.indocyber.common.base.BaseFragment
import id.indocyber.moviedbassignment.R
import id.indocyber.moviedbassignment.adapter.PagingLoadStateAdapter
import id.indocyber.moviedbassignment.adapter.ReviewItemAdapter
import id.indocyber.moviedbassignment.databinding.LayoutMovieDetailBinding
import id.indocyber.moviedbassignment.view_model.MovieDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<LayoutMovieDetailBinding, MovieDetailViewModel>() {
    override val viewModel: MovieDetailViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_movie_detail
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val adapter = ReviewItemAdapter()
    private val stateAdapter = PagingLoadStateAdapter {
        onRetry()
    }

    override fun bindingExt(binding: LayoutMovieDetailBinding) {
        super.bindingExt(binding)
        viewModel.getMovieDetailData(args.movieId.orEmpty())
        viewModel.getMovieReviewData(args.movieId.orEmpty())
        viewModel.getMovieVideoData(args.movieId.orEmpty())
        viewModel.movieDetailData.observe(this) {
            when (it) {
                is AppResponseSuccess -> {
                    binding.data = it.data
                    binding.movieGenres = it.data.genres.joinToString(",") { genre -> genre.name }
                    binding.buttonRetry.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                }
                is AppResponseLoading -> {
                    binding.buttonRetry.visibility = View.GONE
                    binding.loadingBar.visibility = View.VISIBLE
                }
                is AppResponseError -> {
                    binding.buttonRetry.visibility = View.VISIBLE
                    binding.loadingBar.visibility = View.GONE
                    alertDialogBuilder("Error", it.e?.message.toString())
                }
            }
        }
        viewModel.movieReviewData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
        viewModel.movieVideoData.observe(this) {
            when (it) {
                is AppResponseSuccess -> {
                    val listener = object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            val videoId = it.data.key
                            youTubePlayer.cueVideo(videoId, 0f)
                            val defaultPlayerUiController =
                                DefaultPlayerUiController(binding.viewYoutubePlayer, youTubePlayer)
                            binding.viewYoutubePlayer.setCustomPlayerUi(defaultPlayerUiController.rootView)
                        }
                    }
                    val option = IFramePlayerOptions.Builder().controls(0).build()
                    binding.viewYoutubePlayer.initialize(listener, option)
                    binding.buttonRetry.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                }
                is AppResponseLoading -> {
                    binding.buttonRetry.visibility = View.GONE
                    binding.loadingBar.visibility = View.VISIBLE
                }
                is AppResponseError -> {
                    binding.buttonRetry.visibility = View.VISIBLE
                    binding.viewYoutubePlayer.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                    alertDialogBuilder("Attention", it.e?.message.toString())
                }
            }
        }
        binding.recyclerViewMovieReview.adapter = adapter.withLoadStateFooter(stateAdapter)
        binding.buttonRetry.setOnClickListener {
            viewModel.getMovieDetailData(args.movieId.orEmpty())
            viewModel.getMovieReviewData(args.movieId.orEmpty())
            viewModel.getMovieVideoData(args.movieId.orEmpty())
        }
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.recyclerViewMovieReview.visibility = View.GONE
                binding.buttonRetry.visibility = View.VISIBLE
                binding.loadingBar.visibility = View.GONE
                binding.textNoReview.visibility = View.GONE
            } else if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                binding.recyclerViewMovieReview.visibility = View.GONE
                binding.buttonRetry.visibility = View.GONE
                binding.loadingBar.visibility = View.VISIBLE
                binding.textNoReview.visibility = View.GONE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount != 0) {
                binding.recyclerViewMovieReview.visibility = View.VISIBLE
                binding.buttonRetry.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
                binding.textNoReview.visibility = View.GONE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                binding.recyclerViewMovieReview.visibility = View.VISIBLE
                binding.buttonRetry.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
                binding.textNoReview.visibility = View.VISIBLE
            }
        }
    }

    private fun onRetry() {
        adapter.retry()
    }
}