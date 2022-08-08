package id.indocyber.common.entity.genre_list


import com.google.gson.annotations.SerializedName

data class GenreList(
    @SerializedName("genres")
    val genres: List<Genre>
)