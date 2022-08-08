package id.indocyber.api_service.use_case

import id.indocyber.api_service.service.MovieDetailService
import id.indocyber.common.AppResponse
import kotlinx.coroutines.flow.flow

class GetMovieDetailUseCase(private val movieDetailService: MovieDetailService) {
    operator fun invoke(movieId: String) = flow {
        emit(AppResponse.loading())
        val result = movieDetailService.getMovieDetail(movieId)
        if (result.isSuccessful) {
            emit(result.body()?.let {
                AppResponse.success(it)
            } ?: kotlin.run {
                AppResponse.error(Exception("Data is empty: " + result.message()))
            })
        } else {
            emit(AppResponse.error(Exception("Request is not successful: " + result.message())))
        }
    }
}