package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.repository.allmovies.AllPopularRepository
import com.example.movieapp.utils.paging.AllPopularPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AllPopularViewModel @Inject constructor(private val popularRepository: AllPopularRepository) :
    ViewModel() {


        val allPopular=Pager(PagingConfig(20,maxSize = 200, enablePlaceholders = false)){


            AllPopularPagingSource(popularRepository)





        }.flow.cachedIn(viewModelScope)

}