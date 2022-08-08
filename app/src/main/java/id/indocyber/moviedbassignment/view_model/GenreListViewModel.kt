package id.indocyber.moviedbassignment.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.SelectionTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.use_case.GetGenreListUseCase
import id.indocyber.common.AppResponse
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.genre_list.Genre
import id.indocyber.moviedbassignment.fragment.GenreListFragmentDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreListViewModel @Inject constructor(
    application: Application,
    private val getGenreListUseCase: GetGenreListUseCase
) : BaseViewModel(application) {
    val genreListData = MutableLiveData<AppResponse<List<Genre>>>()
    var selection: SelectionTracker<Long>? = null

    init {
        getGenreListData()
    }

    fun getGenreListData() {
        viewModelScope.launch {
            getGenreListUseCase.invoke().collect {
                genreListData.postValue(it)
            }
        }
    }

    fun navigateToMovieList(){
        navigate(
            GenreListFragmentDirections.actionGenreListFragmentToMovieListFragment(
                selection?.selection?.toMutableList().orEmpty().joinToString(",")
            )
        )
    }

}