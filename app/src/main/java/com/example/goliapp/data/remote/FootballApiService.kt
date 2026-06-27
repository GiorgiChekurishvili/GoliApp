package com.example.goliapp.data.remote

import com.example.goliapp.data.remote.dto.ApiResponse
import com.example.goliapp.data.remote.dto.FixtureResponseDto
import com.example.goliapp.data.remote.dto.LeagueResponseDto
import com.example.goliapp.data.remote.dto.StandingsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApiService {

    @GET("fixtures")
    suspend fun getFixturesByDate(
        @Query("date") date: String,
        @Query("timezone") timezone: String = "Asia/Tbilisi"
    ): ApiResponse<FixtureResponseDto>

    @GET("fixtures")
    suspend fun getFixturesByLeague(
        @Query("league") leagueId: Int,
        @Query("season") season: Int,
        @Query("timezone") timezone: String = "Asia/Tbilisi"
    ): ApiResponse<FixtureResponseDto>

    @GET("fixtures")
    suspend fun getFixtureById(
        @Query("id") fixtureId: Int
    ): ApiResponse<FixtureResponseDto>

    @GET("fixtures")
    suspend fun getLiveFixtures(
        @Query("live") live: String = "all"
    ): ApiResponse<FixtureResponseDto>

    @GET("standings")
    suspend fun getStandings(
        @Query("league") leagueId: Int,
        @Query("season") season: Int
    ): ApiResponse<StandingsResponseDto>

    @GET("leagues")
    suspend fun getLeagues(
        @Query("current") current: Boolean = true,
        @Query("type") type: String = "League"
    ): ApiResponse<LeagueResponseDto>
}
