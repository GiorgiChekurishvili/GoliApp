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
    val countryFlagUrl: String?,
)