package com.example.movieapp.data.repository.allmovies

import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import javax.inject.Inject

class AllUpComingRepository@Inject constructor(private val api: ApiServices) {



    suspend fun allUpComing(page:Int)=api.allUpComing(API_KEY,page)







}