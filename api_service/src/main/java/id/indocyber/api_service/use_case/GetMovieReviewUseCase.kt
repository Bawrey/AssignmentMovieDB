package id.indocyber.api_service.use_case

import id.indocyber.api_service.paging.MovieReviewPagingDataSource
import id.indocyber.api_service.service.MovieReviewService

class GetMovieReviewUseCase(private val movieReviewService: MovieReviewService) {
    operator fun invoke(movieId: String) =
        MovieReviewPagingDataSource.createPager(movieReviewService, 10, movieId)
}