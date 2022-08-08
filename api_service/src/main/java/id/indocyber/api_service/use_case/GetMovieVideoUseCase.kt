package id.indocyber.api_service.use_case

import id.indocyber.api_service.Constants.API_KEY
import id.indocyber.api_service.service.MovieVideoService
import id.indocyber.common.AppResponse
import kotlinx.coroutines.flow.flow

class GetMovieVideoUseCase(private val movieVideoService: MovieVideoService) {
    operator fun invoke(movieId: String) = flow {
        emit(AppResponse.loading())
        val result = movieVideoService.getMovieVideo(movieId, API_KEY)
        if (result.isSuccessful) {
            emit(result.body()?.videos?.let { list ->
                if (list.isNotEmpty()) AppResponse.success(list.filter { it.type == "Trailer" }[0])
                else AppResponse.error(Exception("There are no video trailer"))
            } ?: kotlin.run {
                AppResponse.error(Exception("Data is empty: " + result.message()))
            })
        } else {
            emit(AppResponse.error((Exception("Request is not successful: " + result.message()))))
        }
    }
}