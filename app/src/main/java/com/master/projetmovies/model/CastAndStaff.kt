package com.master.projetmovies.model

data class CastAndStaff (
    val id:Int,
    val listCast:List<Actor>,
    val listStaff: List<Staff>
)