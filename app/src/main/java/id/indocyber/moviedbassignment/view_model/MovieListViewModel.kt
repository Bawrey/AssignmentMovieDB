package id.indocyber.moviedbassignment.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.use_case.GetMovieListUseCase
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.movie_list.Movie
import id.indocyber.moviedbassignment.fragment.MovieListFragmentDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    application: Application,
    private val getMovieListUseCase: GetMovieListUseCase
) :
    BaseViewModel(application) {

    var movieListData = MutableLiveData<PagingData<Movie>>()

    fun getMovieListData(genreIds: String) {
        if (movieListData.value == null) {
            viewModelScope.launch {
                getMovieListUseCase.invoke(genreIds).flow.cachedIn(this).collect {
                    movieListData.postValue(it)
                }
            }
        } else {
            movieListData.postValue(movieListData.value)
        }
    }

    fun navigateToMovieDetail(movieId: String) {
        navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieId))
    }
}