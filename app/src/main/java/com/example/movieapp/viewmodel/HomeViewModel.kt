package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.model.home.ResponseTvOnTheAir
import com.example.movieapp.data.model.home.ResponseUpComing
import com.example.movieapp.data.repository.home.HomeRepository
import com.example.movieapp.utils.DEFAULT_PAGE
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class HomeViewModel@Inject constructor(private val repository: HomeRepository):ViewModel() {









    val topRatedLiveData=MutableLiveData<NetworkRequest<ResponsePopular>>()



    fun callTopRatedApi()=viewModelScope.launch{


        topRatedLiveData.value= NetworkRequest.Loading()


        val response=repository.getTopRatedList()


        topRatedLiveData.value= NetworkResponse(response).generateResponse()




    }














    val popularLiveData=MutableLiveData<NetworkRequest<ResponsePopular>>()



    fun callPopularApi()=viewModelScope.launch{


        popularLiveData.value= NetworkRequest.Loading()


        val response=repository.getPopularList(DEFAULT_PAGE)


        popularLiveData.value= NetworkResponse(response).generateResponse()




    }






    val tvListLiveData=MutableLiveData<NetworkRequest<ResponseMovies>>()



    fun callTvListApi()=viewModelScope.launch{


        tvListLiveData.value= NetworkRequest.Loading()


        val response=repository.tvList()


        tvListLiveData.value= NetworkResponse(response).generateResponse()




    }







    val tvOnTheAirLiveData=MutableLiveData<NetworkRequest<ResponseTvOnTheAir>>()



    fun callTvOnTheAirApi()=viewModelScope.launch{


        tvOnTheAirLiveData.value= NetworkRequest.Loading()


        val response=repository.tvOnTheAir()


        tvOnTheAirLiveData.value= NetworkResponse(response).generateResponse()




    }





    val upComingLiveData=MutableLiveData<NetworkRequest<ResponseUpComing>>()



    fun callUpComingApi()=viewModelScope.launch{


        upComingLiveData.value= NetworkRequest.Loading()


        val response=repository.upComing()


        upComingLiveData.value= NetworkResponse(response).generateResponse()




    }







}