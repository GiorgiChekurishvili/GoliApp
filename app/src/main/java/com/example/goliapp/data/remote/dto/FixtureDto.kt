package com.example.goliapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FixtureResponseDto(
    @SerializedName("fixture") val fixture: FixtureDto,
    @SerializedName("league") val league: FixtureLeagueDto,
    @SerializedName("teams") val teams: TeamsDto,
    @SerializedName("goals") val goals: GoalsDto,
    @SerializedName("score") val score: ScoreDto? = null
)

data class FixtureDto(
    @SerializedName("id") val id: Int,
    @SerializedName("referee") val referee: String? = null,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("date") val date: String,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("status") val status: FixtureStatusDto
)

data class FixtureStatusDto(
    @SerializedName("long") val long: String,
    @SerializedName("short") val short: String,
    @SerializedName("elapsed") val elapsed: Int? = null
)

data class FixtureLeagueDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("flag") val flag: String? = null,
    @SerializedName("season") val season: Int,
    @SerializedName("round") val round: String
)

data class TeamsDto(
    @SerializedName("home") val home: TeamInfoDto,
    @SerializedName("away") val away: TeamInfoDto
)

data class TeamInfoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("winner") val winner: Boolean? = null
)

data class GoalsDto(
    @SerializedName("home") val home: Int? = null,
    @SerializedName("away") val away: Int? = null
)

data class ScoreDto(
    @SerializedName("halftime") val halftime: GoalsDto? = null,
    @SerializedName("fulltime") val fulltime: GoalsDto? = null,
    @SerializedName("extratime") val extratime: GoalsDto? = null,
    @SerializedName("penalty") val penalty: GoalsDto? = null
)
