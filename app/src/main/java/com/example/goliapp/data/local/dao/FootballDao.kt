package com.example.goliapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goliapp.data.local.entity.MatchEntity // Assuming your local match entity name
import com.example.goliapp.data.local.entity.LeagueEntity // Assuming your local league entity name
import kotlinx.coroutines.flow.Flow

@Dao
interface FootballDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<MatchEntity>)

    @Query("SELECT * FROM matches WHERE (:leagueId IS NULL OR leagueId = :leagueId) ORDER BY date DESC")
    fun getTodayMatches(leagueId: Int?): Flow<List<MatchEntity>>

    @Query("SELECT * FROM matches WHERE id = :matchId LIMIT 1")
    fun getMatchDetail(matchId: Int): Flow<MatchEntity?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagues(leagues: List<LeagueEntity>)

    @Query("SELECT * FROM leagues ORDER BY name ASC")
    fun getLeagues(): Flow<List<LeagueEntity>>


    @Query("DELETE FROM matches")
    suspend fun clearAllMatches()

    @Query("DELETE FROM leagues")
    suspend fun clearAllLeagues()
}
