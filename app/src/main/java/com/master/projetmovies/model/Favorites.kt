package com.master.projetmovies.model
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Favorites(val listMovies:List<Movie>? = null)