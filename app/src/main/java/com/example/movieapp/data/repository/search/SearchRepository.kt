package com.example.movieapp.data.repository.search

import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import javax.inject.Inject

class SearchRepository@Inject constructor(private val api: ApiServices) {





    suspend fun searchByKeyword(keyWord: String) = api.searchByKeyword(keyWord, API_KEY)



}