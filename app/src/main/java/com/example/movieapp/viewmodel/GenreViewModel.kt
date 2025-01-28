package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.model.genre.ResponseGenreList
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.model.home.ResponseUpComing
import com.example.movieapp.data.repository.genre.GenreRepository
import com.example.movieapp.data.repository.home.HomeRepository
import com.example.movieapp.utils.DEFAULT_PAGE
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class GenreViewModel@Inject constructor(private val repository: GenreRepository):ViewModel() {









    val genreListLiveData=MutableLiveData<NetworkRequest<ResponseGenreList>>()



    fun callGenreApi()=viewModelScope.launch{


        genreListLiveData.value= NetworkRequest.Loading()


        val response=repository.getGenres()


        genreListLiveData.value= NetworkResponse(response).generateResponse()




    }





















}