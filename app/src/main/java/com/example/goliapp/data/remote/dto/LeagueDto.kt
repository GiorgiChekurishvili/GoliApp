package com.example.goliapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LeagueResponseDto(
    @SerializedName("league") val league: LeagueDto,
    @SerializedName("country") val country: LeagueCountryDto,
    @SerializedName("seasons") val seasons: List<LeagueSeasonDto>
)

data class LeagueDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("logo") val logo: String
)

data class LeagueCountryDto(
    @SerializedName("name") val name: String,
    @SerializedName("code") val code: String? = null,
    @SerializedName("flag") val flag: String? = null
)

data class LeagueSeasonDto(
    @SerializedName("year") val year: Int,
    @SerializedName("start") val start: String,
    @SerializedName("end") val end: String,
    @SerializedName("current") val current: Boolean
)
