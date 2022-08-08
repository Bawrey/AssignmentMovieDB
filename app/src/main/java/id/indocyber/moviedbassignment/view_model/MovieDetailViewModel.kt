package id.indocyber.moviedbassignment.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.use_case.GetMovieDetailUseCase
import id.indocyber.api_service.use_case.GetMovieReviewUseCase
import id.indocyber.api_service.use_case.GetMovieVideoUseCase
import id.indocyber.common.AppResponse
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.movie_detail.MovieDetail
import id.indocyber.common.entity.movie_review.Review
import id.indocyber.common.entity.movie_video.Video
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    application: Application,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieReviewUseCase: GetMovieReviewUseCase,
    private val getMovieVideoUseCase: GetMovieVideoUseCase
) : BaseViewModel(application) {
    val movieDetailData = MutableLiveData<AppResponse<MovieDetail>>()
    var movieReviewData = MutableLiveData<PagingData<Review>>()
    val movieVideoData = MutableLiveData<AppResponse<Video>>()


    fun getMovieDetailData(movieId: String) {
        viewModelScope.launch {
            getMovieDetailUseCase.invoke(movieId).collect {
                movieDetailData.postValue(it)
            }
        }
    }

    fun getMovieReviewData(movieId: String) {
        if (movieReviewData.value == null) {
            viewModelScope.launch {
                getMovieReviewUseCase.invoke(movieId).flow.cachedIn(this).collect {
                    movieReviewData.postValue(it)
                }
            }
        } else {
            movieReviewData.postValue(movieReviewData.value)
        }
    }

    fun getMovieVideoData(movieId: String) {
        viewModelScope.launch {
            getMovieVideoUseCase.invoke(movieId).collect {
                movieVideoData.postValue(it)
            }
        }
    }


}