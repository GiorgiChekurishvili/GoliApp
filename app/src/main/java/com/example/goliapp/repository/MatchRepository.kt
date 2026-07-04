package com.example.goliapp.repository

import com.example.goliapp.data.local.dao.MatchDao
import com.example.goliapp.data.remote.FootballApiService
import com.example.goliapp.domain.model.Match
import com.example.goliapp.domain.model.toEntity
import com.example.goliapp.domain.model.toMatch
import com.example.goliapp.utils.Constants
import com.example.goliapp.utils.Resource
import com.example.goliapp.utils.todayDateString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MatchRepository @Inject constructor(
    private val api: FootballApiService,
    private val matchDao: MatchDao
) {

    fun getTodayMatches(): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getFixturesByDate(date = todayDateString())
            val matches = response.response.map { it.toMatch() }
            matchDao.insertMatches(matches.map { it.toEntity() })
            emit(Resource.Success(matches))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    fun getMatchesByLeague(leagueId: Int): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getFixturesByLeague(
                leagueId = leagueId,
                season = Constants.CURRENT_SEASON
            )
            val matches = response.response.map { it.toMatch() }
            matchDao.insertMatches(matches.map { it.toEntity() })
            emit(Resource.Success(matches))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    fun getCachedMatchesByLeague(leagueId: Int): Flow<List<Match>> =
        matchDao.getMatchesByLeague(leagueId).map { entities ->
            entities.map { it.toMatch() }
        }

    fun getLiveMatches(): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getLiveFixtures()
            val matches = response.response.map { it.toMatch() }
            emit(Resource.Success(matches))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    fun getMatchById(matchId: Int): Flow<Resource<Match>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getFixtureById(fixtureId = matchId)
            val match = response.response.firstOrNull()?.toMatch()
            if (match != null) {
                emit(Resource.Success(match))
            } else {
                val cached = matchDao.getMatchById(matchId)?.toMatch()
                if (cached != null) emit(Resource.Success(cached))
                else emit(Resource.Error("Match not found"))
            }
        } catch (e: Exception) {
            val cached = matchDao.getMatchById(matchId)?.toMatch()
            if (cached != null) emit(Resource.Success(cached))
            else emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
