package com.example.movieapp.utils.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.data.db.FavoriteDatabase
import com.example.movieapp.data.db.FavoriteEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    @Singleton
    fun provideEntity() = FavoriteEntity()


    @Provides
    @Singleton
    fun provideDao(db: FavoriteDatabase) = db.favoriteDao()


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, FavoriteDatabase::class.java, "favoriteDatabase")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()


}