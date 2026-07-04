package com.example.goliapp.repository

import com.example.goliapp.data.local.dao.MatchDao
import com.example.goliapp.data.remote.FootballApiService
import com.example.goliapp.domain.model.League
import com.example.goliapp.domain.model.Match
import com.example.goliapp.domain.model.toEntity
import com.example.goliapp.domain.model.toLeague
import com.example.goliapp.domain.model.toMatch
import com.example.goliapp.utils.Constants
import com.example.goliapp.utils.Resource
import com.example.goliapp.utils.todayDateString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FootballRepository @Inject constructor(
    private val api: FootballApiService,
    private val matchDao: MatchDao
) {

    fun getTodayMatches(leagueId: Int?): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading)
        try {
            val response = if (leagueId == null) {
                api.getFixturesByDate(date = todayDateString())
            } else {
                api.getFixturesByLeague(
                    leagueId = leagueId,
                    season = Constants.CURRENT_SEASON
                )
            }
            val matches = response.response.map { it.toMatch() }
            matchDao.insertMatches(matches.map { it.toEntity() })
            emit(Resource.Success(matches))
        } catch (e: HttpException) {
            emit(cachedOrError(leagueId, e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(cachedOrError(leagueId, "Couldn't reach server. Check your internet connection."))
        }
    }

    fun getMatchDetail(matchId: Int): Flow<Resource<Match>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getFixtureById(fixtureId = matchId)
            val match = response.response.firstOrNull()?.toMatch()
            if (match != null) {
                matchDao.insertMatches(listOf(match.toEntity()))
                emit(Resource.Success(match))
            } else {
                val cached = matchDao.getMatchById(matchId)?.toMatch()
                if (cached != null) emit(Resource.Success(cached))
                else emit(Resource.Error("Match not found"))
            }
        } catch (e: HttpException) {
            val cached = matchDao.getMatchById(matchId)?.toMatch()
            if (cached != null) emit(Resource.Success(cached))
            else emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            val cached = matchDao.getMatchById(matchId)?.toMatch()
            if (cached != null) emit(Resource.Success(cached))
            else emit(Resource.Error("Network error. Please try again."))
        }
    }

    fun getLeagues(): Flow<Resource<List<League>>> = flow {
        emit(Resource.Loading)
        try {
            val leagues = api.getLeagues().response.map { it.toLeague() }
            emit(Resource.Success(leagues))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error. Please try again."))
        }
    }

    private suspend fun cachedOrError(leagueId: Int?, errorMessage: String): Resource<List<Match>> {
        val cached = if (leagueId != null) {
            matchDao.getMatchesByLeague(leagueId).first()
        } else {
            matchDao.getMatchesByDate(todayDateString()).first()
        }.map { it.toMatch() }
        return if (cached.isNotEmpty()) Resource.Success(cached) else Resource.Error(errorMessage)
    }
}
