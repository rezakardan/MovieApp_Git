package com.example.movieapp.data.repository.allmovies

import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import javax.inject.Inject

class AllPopularRepository @Inject constructor(private val api: ApiServices) {


    suspend fun getAllPopularList(page:Int) = api.getAllPopularList(API_KEY,page)



}