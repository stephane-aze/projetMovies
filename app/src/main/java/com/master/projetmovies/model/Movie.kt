package com.master.projetmovies.model

import com.master.projetmovies.service.dto.GenreDTO

data class Movie(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val description: String,
    val genres: List<GenreDTO>? /*listOf("Adventure","Drama","Sci-Fi")*/,
    val category: String ="PG-13",
    val year: Int =2014,
    val duration: String ="2h 49min",
    val nbReview: Float = 46f,
    val note: Float,
    val nbView: Double? =1.1,
    val metaScore: Int=74,
    var staffList : List<Staff>? = null,
    var actorList: List<Actor>? =null
)