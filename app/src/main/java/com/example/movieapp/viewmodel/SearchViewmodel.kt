package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.detail.ResponseDetail
import com.example.movieapp.data.model.detail.ResponseSimilar
import com.example.movieapp.data.repository.detail.DetailRepository
import com.example.movieapp.data.repository.search.SearchRepository
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewmodel@Inject constructor(private val repository: SearchRepository): ViewModel() {


    val searchByKeywordLiveData = MutableLiveData<NetworkRequest<ResponseSimilar>>()


    fun searchByKeyword(keyWord: String)= viewModelScope.launch {


        searchByKeywordLiveData.value = NetworkRequest.Loading()


        val response = repository.searchByKeyword(keyWord)


        searchByKeywordLiveData.value = NetworkResponse(response).generateResponse()


    }
}