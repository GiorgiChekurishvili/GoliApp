package com.example.goliapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StandingsResponseDto(
    @SerializedName("league") val league: StandingsLeagueDto
)

data class StandingsLeagueDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("flag") val flag: String? = null,
    @SerializedName("season") val season: Int,
    @SerializedName("standings") val standings: List<List<StandingEntryDto>>
)

data class StandingEntryDto(
    @SerializedName("rank") val rank: Int,
    @SerializedName("team") val team: TeamInfoDto,
    @SerializedName("points") val points: Int,
    @SerializedName("goalsDiff") val goalsDiff: Int,
    @SerializedName("group") val group: String? = null,
    @SerializedName("form") val form: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("all") val all: StandingStatsDto,
    @SerializedName("home") val home: StandingStatsDto,
    @SerializedName("away") val away: StandingStatsDto
)

data class StandingStatsDto(
    @SerializedName("played") val played: Int,
    @SerializedName("win") val win: Int,
    @SerializedName("draw") val draw: Int,
    @SerializedName("lose") val lose: Int,
    @SerializedName("goals") val goals: StandingGoalsDto
)

data class StandingGoalsDto(
    @SerializedName("for") val goalsFor: Int,
    @SerializedName("against") val goalsAgainst: Int
)
