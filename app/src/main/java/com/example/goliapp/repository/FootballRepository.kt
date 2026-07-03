package com.example.goliapp.repository

import com.example.goliapp.data.local.dao.FootballDao
import com.example.goliapp.data.remote.FootballApiService
// ეს იმპორტებია ყველაზე მნიშვნელოვანი, რადგან Mappers.kt-ში გაქვს ეს ფუნქციები:
import com.example.goliapp.domain.model.toLeague
import com.example.goliapp.domain.model.toMatch
import com.example.goliapp.domain.model.Match
import com.example.goliapp.domain.model.League
import com.example.goliapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FootballRepository @Inject constructor(
    private val apiService: FootballApiService,
    private val footballDao: FootballDao
) {

    fun getTodayMatches(date: String): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getFixturesByDate(date = date)
            // response.response არის List<FixtureResponseDto>
            // it.toMatch() ახლა იმუშავებს, რადგან Mappers.kt-დან დავაიმპორტეთ
            val matches = response.response.map { it.toMatch() }
            emit(Resource.Success(matches))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    fun getMatchDetail(matchId: Int): Flow<Resource<Match>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getFixtureById(fixtureId = matchId)
            val matchDto = response.response.firstOrNull()
            if (matchDto != null) {
                // matchDto.toMatch() მუშაობს Mappers.kt-ის წყალობით
                emit(Resource.Success(matchDto.toMatch()))
            } else {
                emit(Resource.Error("Match not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    fun getLeagues(): Flow<Resource<List<League>>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getLeagues()
            // it.toLeague() მუშაობს Mappers.kt-ის წყალობით
            val leagues = response.response.map { it.toLeague() }
            emit(Resource.Success(leagues))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}