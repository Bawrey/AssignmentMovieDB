package id.indocyber.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.Constants.API_KEY
import id.indocyber.api_service.service.MovieReviewService
import id.indocyber.common.entity.movie_review.Review

class MovieReviewPagingDataSource(
    private val movieReviewService: MovieReviewService, private val movieId: String
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val page = params.key ?: 1
        val prevPageKey = if (page == 1) null else page - 1
        return try {
            val result = movieReviewService.getMovieReview(movieId, page, API_KEY)
            return if (result.isSuccessful) {
                result.body()?.let {
                    val nexPageKey = if (it.reviews.isEmpty()) null else page + 1
                    LoadResult.Page(data = it.reviews, prevKey = prevPageKey, nextKey = nexPageKey)
                } ?: kotlin.run {
                    LoadResult.Error(Exception("Data is empty: " + result.message()))
                }
            } else {
                LoadResult.Error(Exception("Request is not successful: " + result.message()))
            }
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
    companion object{
        fun createPager(
            movieReviewService: MovieReviewService,
            pageSize:Int,
            movieId:String
        ):Pager<Int,Review> = Pager(config = PagingConfig(pageSize), pagingSourceFactory = {
            MovieReviewPagingDataSource(movieReviewService,movieId)
        })
    }
}