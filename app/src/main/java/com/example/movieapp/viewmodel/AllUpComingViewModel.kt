package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.repository.allmovies.AllUpComingRepository
import com.example.movieapp.utils.paging.AllUpComingPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AllUpComingViewModel@Inject constructor(private val repository: AllUpComingRepository):ViewModel() {


    val tvListLiveData=Pager(PagingConfig(20,maxSize = 200, enablePlaceholders = false)){


        AllUpComingPagingSource(repository)







    }.flow.cachedIn(viewModelScope)






}