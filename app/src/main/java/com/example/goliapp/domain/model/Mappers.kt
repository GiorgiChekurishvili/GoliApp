package com.example.goliapp.domain.model

import com.example.goliapp.data.local.entity.FavouriteMatchEntity
import com.example.goliapp.data.local.entity.MatchEntity
import com.example.goliapp.data.remote.dto.FixtureResponseDto
import com.example.goliapp.data.remote.dto.LeagueResponseDto
import com.example.goliapp.data.remote.dto.StandingEntryDto

fun FixtureResponseDto.toMatch() = Match(
    id = fixture.id,
    date = fixture.date,
    statusShort = fixture.status.short,
    statusLong = fixture.status.long,
    elapsed = fixture.status.elapsed,
    leagueId = league.id,
    leagueName = league.name,
    leagueLogo = league.logo,
    round = league.round,
    homeTeamId = teams.home.id,
    homeTeamName = teams.home.name,
    homeTeamLogo = teams.home.logo,
    awayTeamId = teams.away.id,
    awayTeamName = teams.away.name,
    awayTeamLogo = teams.away.logo,
    homeGoals = goals.home,
    awayGoals = goals.away,
    season = league.season
)

fun StandingEntryDto.toStanding() = Standing(
    rank = rank,
    teamId = team.id,
    teamName = team.name,
    teamLogo = team.logo,
    points = points,
    played = all.played,
    won = all.win,
    drawn = all.draw,
    lost = all.lose,
    goalsFor = all.goals.goalsFor,
    goalsAgainst = all.goals.goalsAgainst,
    goalDifference = goalsDiff,
    form = form,
    description = description
)

fun LeagueResponseDto.toLeague() = League(
    id = league.id,
    name = league.name,
    logo = league.logo,
    country = country.name,
    flag = country.flag,
    season = seasons.firstOrNull { it.current }?.year ?: 2024
)

fun Match.toEntity() = MatchEntity(
    id = id,
    date = date,
    statusShort = statusShort,
    statusLong = statusLong,
    elapsed = elapsed,
    leagueId = leagueId,
    leagueName = leagueName,
    leagueLogo = leagueLogo,
    round = round,
    homeTeamId = homeTeamId,
    homeTeamName = homeTeamName,
    homeTeamLogo = homeTeamLogo,
    awayTeamId = awayTeamId,
    awayTeamName = awayTeamName,
    awayTeamLogo = awayTeamLogo,
    homeGoals = homeGoals,
    awayGoals = awayGoals,
    season = season
)

fun MatchEntity.toMatch() = Match(
    id = id,
    date = date,
    statusShort = statusShort,
    statusLong = statusLong,
    elapsed = elapsed,
    leagueId = leagueId,
    leagueName = leagueName,
    leagueLogo = leagueLogo,
    round = round,
    homeTeamId = homeTeamId,
    homeTeamName = homeTeamName,
    homeTeamLogo = homeTeamLogo,
    awayTeamId = awayTeamId,
    awayTeamName = awayTeamName,
    awayTeamLogo = awayTeamLogo,
    homeGoals = homeGoals,
    awayGoals = awayGoals,
    season = season
)

fun Match.toFavouriteEntity() = FavouriteMatchEntity(
    id = id,
    date = date,
    statusShort = statusShort,
    statusLong = statusLong,
    elapsed = elapsed,
    leagueId = leagueId,
    leagueName = leagueName,
    leagueLogo = leagueLogo,
    round = round,
    homeTeamId = homeTeamId,
    homeTeamName = homeTeamName,
    homeTeamLogo = homeTeamLogo,
    awayTeamId = awayTeamId,
    awayTeamName = awayTeamName,
    awayTeamLogo = awayTeamLogo,
    homeGoals = homeGoals,
    awayGoals = awayGoals,
    season = season
)

fun FavouriteMatchEntity.toMatch() = Match(
    id = id,
    date = date,
    statusShort = statusShort,
    statusLong = statusLong,
    elapsed = elapsed,
    leagueId = leagueId,
    leagueName = leagueName,
    leagueLogo = leagueLogo,
    round = round,
    homeTeamId = homeTeamId,
    homeTeamName = homeTeamName,
    homeTeamLogo = homeTeamLogo,
    awayTeamId = awayTeamId,
    awayTeamName = awayTeamName,
    awayTeamLogo = awayTeamLogo,
    homeGoals = homeGoals,
    awayGoals = awayGoals,
    season = season
)
