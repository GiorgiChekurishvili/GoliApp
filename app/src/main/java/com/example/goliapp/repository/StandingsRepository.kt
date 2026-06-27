package com.example.goliapp.repository

import com.example.goliapp.data.remote.FootballApiService
import com.example.goliapp.domain.model.Standing
import com.example.goliapp.domain.model.toStanding
import com.example.goliapp.utils.Constants
import com.example.goliapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StandingsRepository @Inject constructor(
    private val api: FootballApiService
) {
    fun getStandings(
        leagueId: Int,
        season: Int = Constants.CURRENT_SEASON
    ): Flow<Resource<List<Standing>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getStandings(leagueId = leagueId, season = season)
            val standings = response.response
                .firstOrNull()
                ?.league
                ?.standings
                ?.firstOrNull()
                ?.map { it.toStanding() }
                ?: emptyList()
            emit(Resource.Success(standings))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
