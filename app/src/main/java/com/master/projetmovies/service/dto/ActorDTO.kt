package com.master.projetmovies.service.dto

import com.google.gson.annotations.SerializedName

data class ActorDTO (
    val character:String?,
    val name:String,
    @SerializedName("profile_path") val picture:String?
)