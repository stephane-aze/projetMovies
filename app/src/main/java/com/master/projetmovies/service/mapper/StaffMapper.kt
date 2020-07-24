package com.master.projetmovies.service.mapper

import com.master.projetmovies.model.Staff
import com.master.projetmovies.service.dto.StaffDTO

class StaffMapper {
    fun map(dto: List<StaffDTO>) = dto.map { mapActor(it) }

    private fun mapActor(it: StaffDTO): Staff {
        return with(it) {
            Staff(name = name?:"",job = job?:"",profilePath = profilePath?:"")
        }
    }

}