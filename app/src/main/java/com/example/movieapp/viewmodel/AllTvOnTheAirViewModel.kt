package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.repository.allmovies.AllTvOnTheAirRepository
import com.example.movieapp.utils.paging.AllTvOnTheAirPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AllTvOnTheAirViewModel@Inject constructor(private val repository: AllTvOnTheAirRepository):ViewModel() {


    val allTvOnTheAirLiveData=Pager(PagingConfig(20,maxSize = 200, enablePlaceholders = false)){


        AllTvOnTheAirPagingSource(repository)







    }.flow.cachedIn(viewModelScope)






}