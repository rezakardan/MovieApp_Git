package com.example.movieapp.data.repository.allmovies

import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import javax.inject.Inject

class AllTvOnTheAirRepository@Inject constructor(private val api: ApiServices) {



    suspend fun allTvOnTheAir(page:Int)=api.allTvOnTheAir(API_KEY,page)







}