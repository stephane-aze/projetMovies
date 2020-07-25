package com.master.projetmovies.service.mapper


import com.master.projetmovies.misc.toConversionDuration
import com.master.projetmovies.misc.toConversionYear
import com.master.projetmovies.model.Movie
import com.master.projetmovies.service.dto.MovieDTO


class MovieMapper {
    fun map(dto: List<MovieDTO>) = dto.map { mapMovie(it) }

    private fun mapMovie(movieDTO: MovieDTO): Movie {
        return with(movieDTO) {
            duration?.let {
                Movie(title = title,description= overview,note = rating,imageUrl = posterPath?:"",id = id,genres = emptyList(),duration = duration.toConversionDuration())
            }
            Movie(title = title,description= overview,note = rating,imageUrl = posterPath?:"",id = id,genres = emptyList())
        }
    }

}

