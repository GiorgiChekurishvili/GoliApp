package com.example.goliapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_matches")
data class FavouriteMatchEntity(
    @PrimaryKey val id: Int,
    val date: String,
    val statusShort: String,
    val statusLong: String,
    val elapsed: Int?,
    val leagueId: Int,
    val leagueName: String,
    val leagueLogo: String,
    val round: String,
    val homeTeamId: Int,
    val homeTeamName: String,
    val homeTeamLogo: String,
    val awayTeamId: Int,
    val awayTeamName: String,
    val awayTeamLogo: String,
    val homeGoals: Int?,
    val awayGoals: Int?,
    val season: Int
)
