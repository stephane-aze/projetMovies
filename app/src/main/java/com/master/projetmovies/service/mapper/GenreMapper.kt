package com.master.projetmovies.service.mapper

import com.master.projetmovies.model.Genre
import com.master.projetmovies.model.Movie
import com.master.projetmovies.service.dto.GenreDTO
import com.master.projetmovies.service.dto.MovieDTO

class GenreMapper {
    fun map(dto: List<GenreDTO>) = dto.map { mapGenre(it) }

    private fun mapGenre(genreDTO: GenreDTO): Genre {
        return with(genreDTO) {

            Genre(id=id,name = name)
        }
    }
    private fun conversionDuration(duration: Int):String{
        val hours: Int = duration / 60
        val minutes: Int = duration.rem(60)
        return "${hours}h${minutes}"
    }
}