package com.example.movieapp.data.repository.allmovies

import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import javax.inject.Inject

class AllTvListRepository@Inject constructor(private val api: ApiServices) {



    suspend fun allTvList(page:Int)=api.allTvList(API_KEY,page)







}