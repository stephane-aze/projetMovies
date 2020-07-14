package com.master.projetmovies.service.mapper

import com.master.projetmovies.model.Actor
import com.master.projetmovies.service.dto.ActorDTO

class CastMapper {
    fun map(dto: List<ActorDTO>) = dto.map { mapActor(it) }

    private fun mapActor(it: ActorDTO): Actor {
        return with(it) {
            Actor(name = name,characterName = character?:"",thumbnail = picture?:"")
        }
    }

}