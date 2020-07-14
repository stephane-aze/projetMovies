package com.master.projetmovies.service.dto

class GetCastAndStaffResponse(
    val id: Int?,
    val cast: List<ActorDTO>?,
    val crew: List<StaffDTO>?
)