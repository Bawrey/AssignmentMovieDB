package id.indocyber.common.entity.movie_video


import com.google.gson.annotations.SerializedName

data class MovieVideo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val videos: List<Video>
)