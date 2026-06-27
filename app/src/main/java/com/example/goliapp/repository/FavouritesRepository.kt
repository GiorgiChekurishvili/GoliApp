package com.example.goliapp.repository

import com.example.goliapp.data.local.dao.FavouriteDao
import com.example.goliapp.domain.model.Match
import com.example.goliapp.domain.model.toFavouriteEntity
import com.example.goliapp.domain.model.toMatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouritesRepository @Inject constructor(
    private val favouriteDao: FavouriteDao
) {
    fun getAllFavourites(): Flow<List<Match>> =
        favouriteDao.getAllFavourites().map { entities ->
            entities.map { it.toMatch() }
        }

    fun isFavourite(matchId: Int): Flow<Boolean> =
        favouriteDao.isFavourite(matchId)

    suspend fun addFavourite(match: Match) =
        favouriteDao.insertFavourite(match.toFavouriteEntity())

    suspend fun removeFavourite(matchId: Int) =
        favouriteDao.deleteFavouriteById(matchId)
}
