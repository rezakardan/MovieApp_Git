package com.example.movieapp.data.repository.genre

import com.example.movieapp.data.db.FavoriteDao
import com.example.movieapp.data.db.FavoriteEntity
import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import retrofit2.http.Query
import javax.inject.Inject

class GenreRepository@Inject constructor(private val api: ApiServices) {

    suspend  fun getGenres( )=api.getGenres(API_KEY)






}