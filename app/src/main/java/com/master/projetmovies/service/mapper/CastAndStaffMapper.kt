package com.master.projetmovies.service.mapper

import com.master.projetmovies.model.Actor
import com.master.projetmovies.model.CastAndStaff
import com.master.projetmovies.model.Staff
import com.master.projetmovies.service.dto.ActorDTO
import com.master.projetmovies.service.dto.GetCastAndStaffResponse
import com.master.projetmovies.service.dto.StaffDTO

class CastAndStaffMapper {
    fun mapCastAndStaff(dto: GetCastAndStaffResponse):CastAndStaff{
        var listActor:List<Actor>? =null
        var listCrew:List<Staff>? =null

        dto.cast?.let {
            listActor=it.map { actor->mapActor(actor) }
        }
        dto.crew?.let {
            listCrew=it.map { staff->mapStaff(staff) }
        }
        return with(dto){
            CastAndStaff(id=id?:0,listCast = listActor?: emptyList(),listStaff = listCrew?: emptyList())
        }
    }

    private fun mapActor(it: ActorDTO): Actor {
        return with(it) {
            Actor(name = name,characterName = character?:"",thumbnail = picture?: "")
        }
    }

    private fun mapStaff(it: StaffDTO): Staff {
        return with(it) {
            Staff(name = name?:"",job = job?:"",profilePath = profilePath?:"")
        }
    }
}