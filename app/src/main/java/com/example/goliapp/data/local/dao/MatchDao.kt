package com.example.goliapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goliapp.data.local.entity.MatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<MatchEntity>)

    @Query("SELECT * FROM matches WHERE leagueId = :leagueId ORDER BY date ASC")
    fun getMatchesByLeague(leagueId: Int): Flow<List<MatchEntity>>

    @Query("SELECT * FROM matches WHERE date LIKE :date || '%' ORDER BY date ASC")
    fun getMatchesByDate(date: String): Flow<List<MatchEntity>>

    @Query("SELECT * FROM matches WHERE id = :matchId")
    suspend fun getMatchById(matchId: Int): MatchEntity?

    @Query("DELETE FROM matches WHERE leagueId = :leagueId")
    suspend fun deleteMatchesByLeague(leagueId: Int)

    @Query("DELETE FROM matches")
    suspend fun clearAll()
}
