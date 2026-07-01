package com.example.goliapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.goliapp.domain.model.League

@Entity(tableName = "leagues")
data class LeagueEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val logoUrl: String,
    val countryName: String,
    val countryCode: String?,
    val countryFlagUrl: String?
)

fun LeagueEntity.toLeague(): LeagueEntity {
    return LeagueEntity(
        id = this.id,
        name = this.name,
        type = this.type,
        logoUrl = this.logoUrl,
        countryName = this.countryName,
        countryCode = this.countryCode,
        countryFlagUrl = this.countryFlagUrl
    )
}
