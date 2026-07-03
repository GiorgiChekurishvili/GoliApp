package com.example.goliapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.goliapp.data.local.dao.FavouriteDao
import com.example.goliapp.data.local.dao.FootballDao
import com.example.goliapp.data.local.dao.MatchDao
import com.example.goliapp.data.local.entity.FavouriteMatchEntity
import com.example.goliapp.data.local.entity.LeagueEntity
import com.example.goliapp.data.local.entity.MatchEntity
import com.example.goliapp.utils.Constants

@Database(
    entities = [MatchEntity::class, FavouriteMatchEntity::class, LeagueEntity::class],
    version = Constants.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun footballDao(): FootballDao
    abstract fun matchDao(): MatchDao
    abstract fun favouriteDao(): FavouriteDao
}
