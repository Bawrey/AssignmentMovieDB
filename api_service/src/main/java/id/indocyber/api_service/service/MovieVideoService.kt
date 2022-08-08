package id.indocyber.api_service.service

import id.indocyber.api_service.Constants.API_KEY
import id.indocyber.common.entity.movie_video.MovieVideo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieVideoService {
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String = API_KEY
    ): Response<MovieVideo>
}