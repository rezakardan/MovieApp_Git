package com.example.movieapp.data.repository.home

import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import javax.inject.Inject

class HomeRepository@Inject constructor(private val api: ApiServices) {





    suspend fun getTopRatedList() = api.getTopRatedList(API_KEY)

    suspend fun getPopularList(page:Int) = api.getPopularList(API_KEY,page)


    suspend fun tvList()=api.tvList(API_KEY)


    suspend fun tvOnTheAir()= api.tvOnTheAir(API_KEY)


    suspend fun upComing()=api.upComing(API_KEY)

}