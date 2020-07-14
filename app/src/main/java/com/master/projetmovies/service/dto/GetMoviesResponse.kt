package com.master.projetmovies.service.dto

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MovieDTO>,
    @SerializedName("total_pages") val pages: Int
)