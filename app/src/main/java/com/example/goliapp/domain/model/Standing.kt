package com.example.goliapp.domain.model

data class Standing(
    val rank: Int,
    val teamId: Int,
    val teamName: String,
    val teamLogo: String,
    val points: Int,
    val played: Int,
    val won: Int,
    val drawn: Int,
    val lost: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int,
    val form: String?,
    val description: String?
)
