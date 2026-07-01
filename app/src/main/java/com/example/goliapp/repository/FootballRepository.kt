package com.example.goliapp.repository

import com.example.goliapp.data.local.dao.FootballDao
import com.example.goliapp.domain.model.Match
import com.example.goliapp.domain.model.League
import com.example.goliapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FootballRepository @Inject constructor(
    private val footballApi: FootballDao
) {

    fun getTodayMatches(leagueId: Int?): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading)
        try {
            val remoteMatches = footballApi.getTodayMatches(leagueId)

            val domainMatches = remoteMatches.map { it.toMatch() }

            emit(Resource.Success(domainMatches))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    fun getMatchDetail(matchId: Int): Flow<Resource<Match>> = flow {
        emit(Resource.Loading)
        try {
            val remoteMatchDetail = footballApi.getMatchDetail(matchId)
            emit(Resource.Success(remoteMatchDetail.toMatch()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error. Please try again."))
        }
    }

    fun getLeagues(): Flow<Resource<List<League>>> = flow {
        emit(Resource.Loading)
        try {
            val remoteLeagues = footballApi.getLeagues()
            val domainLeagues = remoteLeagues.map { it.toLeague() }
            emit(Resource.Success(domainLeagues))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error. Please try again."))
        }
    }
}