package com.example.goliapp.domain.model

data class Match(
    val id: Int,
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
) {
    val isFinished get() = statusShort == "FT" || statusShort == "AET" || statusShort == "PEN"
    val isLive get() = statusShort == "1H" || statusShort == "HT" || statusShort == "2H" || statusShort == "ET" || statusShort == "P"
    val isScheduled get() = statusShort == "NS"
    val scoreDisplay get() = if (homeGoals != null && awayGoals != null) "$homeGoals - $awayGoals" else "vs"
}
