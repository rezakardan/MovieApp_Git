package com.example.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase:RoomDatabase() {

    abstract fun favoriteDao():FavoriteDao




}