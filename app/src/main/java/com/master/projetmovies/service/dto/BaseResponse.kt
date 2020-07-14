package com.master.projetmovies.service.dto

import com.google.gson.annotations.SerializedName

data class BaseResponse<Item>(
    @SerializedName("page") val page: Int?=null,
    @SerializedName("total_results") val totalResults: Int?=null,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("results") var results: List<Item>
)