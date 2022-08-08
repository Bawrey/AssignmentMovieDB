package id.indocyber.api_service.service

import id.indocyber.api_service.Constants.API_KEY
import id.indocyber.common.entity.movie_list.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListService {
    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("api_key") api_key: String = API_KEY,
        @Query("with_genres") genreIds: String,
        @Query("page") page: Int
    ): Response<MovieList>

}