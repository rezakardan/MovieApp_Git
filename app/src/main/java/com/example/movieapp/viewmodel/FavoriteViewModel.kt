package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.db.FavoriteEntity
import com.example.movieapp.data.repository.FavoriteRepository
import com.example.movieapp.data.repository.detail.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class FavoriteViewModel @Inject constructor(private  val repository: FavoriteRepository) : ViewModel() {

val loadAllDataLiveData=MutableLiveData<List<FavoriteEntity>>()
    fun  getAllData()=viewModelScope.launch {


        repository.getAllData().collect{

            loadAllDataLiveData.value=it


        }



    }










}