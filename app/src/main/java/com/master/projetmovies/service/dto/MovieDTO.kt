package com.master.projetmovies.service.dto

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("runtime") val duration: Int?,
    @SerializedName("genre_ids") val genres: List<Int>
)