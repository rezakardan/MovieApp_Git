package com.example.movieapp.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.repository.allmovies.AllMoviesRepository
import com.example.movieapp.data.repository.home.HomeRepository
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.utils.network.NetworkResponse
import com.example.movieapp.utils.paging.TopRatedPagingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class AllMoviesViewModel@Inject constructor(private val allMoviesRepository: AllMoviesRepository):ViewModel() {




    val allTopRatedMoviesLiveData=Pager(PagingConfig(20,maxSize = 200, enablePlaceholders = false)){


        TopRatedPagingResource(allMoviesRepository)













}.flow.cachedIn(viewModelScope)



}