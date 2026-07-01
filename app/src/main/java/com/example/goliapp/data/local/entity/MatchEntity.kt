package com.example.goliapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.goliapp.domain.model.Match

@Entity(tableName = "matches")
data class MatchEntity(
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

fun MatchEntity.toMatch(): MatchEntity {
    return MatchEntity(
        id = this.id,
        date = this.date,
        statusShort = this.statusShort,
        statusLong = this.statusLong,
        elapsed = this.elapsed,
        leagueId = this.leagueId,
        leagueName = this.leagueName,
        leagueLogo = this.leagueLogo,
        round = this.round,
        homeTeamId = this.homeTeamId,
        homeTeamName = this.homeTeamName,
        homeTeamLogo = this.homeTeamLogo,
        awayTeamId = this.awayTeamId,
        awayTeamName = this.awayTeamName,
        awayTeamLogo = this.awayTeamLogo,
        homeGoals = this.homeGoals,
        awayGoals = this.awayGoals,
        season = this.season
    )
}


fun Match.toMatchEntity(): MatchEntity {
    return MatchEntity(
        id = this.id,
        date = this.date,
        statusShort = this.statusShort,
        statusLong = this.statusLong,
        elapsed = this.elapsed,
        leagueId = this.leagueId,
        leagueName = this.leagueName,
        leagueLogo = this.leagueLogo,
        round = this.round,
        homeTeamId = this.homeTeamId,
        homeTeamName = this.homeTeamName,
        homeTeamLogo = this.homeTeamLogo,
        awayTeamId = this.awayTeamId,
        awayTeamName = this.awayTeamName,
        awayTeamLogo = this.awayTeamLogo,
        homeGoals = this.homeGoals,
        awayGoals = this.awayGoals,
        season = this.season
    )
}