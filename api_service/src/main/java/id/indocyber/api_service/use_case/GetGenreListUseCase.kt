package id.indocyber.api_service.use_case

import id.indocyber.api_service.service.GenreListService
import id.indocyber.common.AppResponse
import kotlinx.coroutines.flow.flow

class GetGenreListUseCase(private val genreListService: GenreListService) {
    operator fun invoke() = flow {
        emit(AppResponse.loading())
        val result = genreListService.getGenreList()
        if (result.isSuccessful) {
            emit(result.body()?.let {
                AppResponse.success(it.genres)
            } ?: kotlin.run {
                AppResponse.error(Exception("Data is empty: " + result.message()))
            })
        } else {
            emit(AppResponse.error(Exception("Request is not successful: " + result.message())))
        }
    }
}