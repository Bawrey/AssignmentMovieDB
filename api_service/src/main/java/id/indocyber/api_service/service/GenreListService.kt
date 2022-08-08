package id.indocyber.api_service.service

import id.indocyber.api_service.Constants.API_KEY
import id.indocyber.common.entity.genre_list.GenreList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreListService {
    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") api_key:String = API_KEY
    ):Response<GenreList>
}