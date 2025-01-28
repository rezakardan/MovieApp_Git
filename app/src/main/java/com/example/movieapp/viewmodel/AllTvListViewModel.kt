package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.repository.allmovies.AllTvListRepository
import com.example.movieapp.utils.paging.AllTvPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class AllTvListViewModel@Inject constructor(private val repository: AllTvListRepository):ViewModel() {


    val tvListLiveData=Pager(PagingConfig(20,maxSize = 200, enablePlaceholders = false)){


        AllTvPagingSource(repository)







    }.flow.cachedIn(viewModelScope)






}