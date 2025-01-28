package com.example.movieapp.data.repository.detail

import androidx.collection.emptyFloatObjectMap
import com.example.movieapp.data.db.FavoriteDao
import com.example.movieapp.data.db.FavoriteEntity
import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import com.example.movieapp.utils.DEFAULT_PAGE
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class DetailRepository@Inject constructor(private val api: ApiServices,private val dao: FavoriteDao) {





    suspend fun movieDetails(id:Int) = api.movieDetails(id,API_KEY)

    suspend fun getSimilarMovies(id: Int) = api.getSimilarMovies(id,API_KEY)

    suspend fun getPlayVideo(id: Int) = api.getPlayVideo(id,API_KEY)

    suspend fun getPopularActor(id: Int) = api.getPopularActor(id,API_KEY)


    suspend fun getAllActorsOfFilm(id:Int)=api.getActorsOfFilm(id, API_KEY)


    suspend fun addToFavorite(entity: FavoriteEntity)=dao.addToFavorite(entity)

    suspend fun deleteFavorite(entity: FavoriteEntity)=dao.deleteFromFavorite(entity)


    fun isInDatabase(id:String)=dao.isInDatabaseOrNo(id)


}