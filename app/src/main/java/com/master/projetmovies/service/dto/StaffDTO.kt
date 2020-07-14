package com.master.projetmovies.service.dto

import com.google.gson.annotations.SerializedName

data class StaffDTO (
    @SerializedName("job") val job: String?,
    @SerializedName("name") val name: String?
)