package com.master.projetmovies.service.mapper


import com.master.projetmovies.model.Movie
import com.master.projetmovies.service.dto.MovieDTO


class MovieMapper {
    fun map(dto: List<MovieDTO>) = dto.map { mapMovie(it) }

    private fun mapMovie(movieDTO: MovieDTO): Movie {
        return with(movieDTO) {
            duration?.let {
                Movie(title = title,description= overview,note = rating,imageUrl = posterPath?:"",id = id,genres = emptyList(),duration = conversionDuration(duration))
            }
            Movie(title = title,description= overview,note = rating,imageUrl = posterPath?:"",id = id,genres = emptyList())
        }
    }
    private fun conversionDuration(duration: Int):String{
        val hours: Int = duration / 60
        val minutes: Int = duration.rem(60)
        return "${hours}h${minutes}"
    }
}

