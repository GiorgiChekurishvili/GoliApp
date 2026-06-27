package com.example.goliapp.di

import android.content.Context
import androidx.room.Room
import com.example.goliapp.data.local.AppDatabase
import com.example.goliapp.data.local.dao.FavouriteDao
import com.example.goliapp.data.local.dao.MatchDao
import com.example.goliapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()

    @Provides
    fun provideMatchDao(db: AppDatabase): MatchDao = db.matchDao()

    @Provides
    fun provideFavouriteDao(db: AppDatabase): FavouriteDao = db.favouriteDao()
}
