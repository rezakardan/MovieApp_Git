package com.example.movieapp.data.repository

import com.example.movieapp.data.db.FavoriteDao
import com.example.movieapp.data.db.FavoriteEntity
import javax.inject.Inject

class FavoriteRepository@Inject constructor(private val dao: FavoriteDao) {

    fun getAllData()=dao.getAllFavorites()


   suspend fun deleteData(entity: FavoriteEntity)=dao.deleteFromFavorite(entity)






}