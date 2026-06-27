package com.example.goliapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goliapp.data.local.entity.FavouriteMatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(match: FavouriteMatchEntity)

    @Delete
    suspend fun deleteFavourite(match: FavouriteMatchEntity)

    @Query("SELECT * FROM favourite_matches ORDER BY date DESC")
    fun getAllFavourites(): Flow<List<FavouriteMatchEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_matches WHERE id = :matchId)")
    fun isFavourite(matchId: Int): Flow<Boolean>

    @Query("DELETE FROM favourite_matches WHERE id = :matchId")
    suspend fun deleteFavouriteById(matchId: Int)
}
