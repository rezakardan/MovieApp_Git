package com.example.movieapp.data.repository.allmovies

import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import javax.inject.Inject

class AllMoviesRepository@Inject constructor(private val api: ApiServices) {





    suspend fun getAllTopRatedList(page:Int) = api.getAllTopRatedList(API_KEY,page)



   // suspend fun tvList()=api.tvList(API_KEY)


   // suspend fun tvOnTheAir()= api.tvOnTheAir(API_KEY)


  //  suspend fun upComing()=api.upComing(API_KEY)

}