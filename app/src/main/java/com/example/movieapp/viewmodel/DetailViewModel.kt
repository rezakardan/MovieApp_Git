package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.db.FavoriteEntity
import com.example.movieapp.data.model.detail.ResponseActor
import com.example.movieapp.data.model.detail.ResponseAllActors
import com.example.movieapp.data.model.detail.ResponseDetail
import com.example.movieapp.data.model.detail.ResponsePlayerVideo
import com.example.movieapp.data.model.detail.ResponseSimilar
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.model.home.ResponseUpComing
import com.example.movieapp.data.repository.detail.DetailRepository
import com.example.movieapp.data.repository.home.HomeRepository
import com.example.movieapp.utils.network.NetworkRequest
import com.example.movieapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel() {


    val movieDetailsLiveData = MutableLiveData<NetworkRequest<ResponseDetail>>()


    fun callMovieDetailsApi(id: Int) = viewModelScope.launch {


        movieDetailsLiveData.value = NetworkRequest.Loading()


        val response = repository.movieDetails(id)


        movieDetailsLiveData.value = NetworkResponse(response).generateResponse()


    }


    val similarMoviesLiveData = MutableLiveData<NetworkRequest<ResponseSimilar>>()


    fun callSimilarMoviesApi(id: Int) = viewModelScope.launch {


        similarMoviesLiveData.value = NetworkRequest.Loading()


        val response = repository.getSimilarMovies(id)


        similarMoviesLiveData.value = NetworkResponse(response).generateResponse()


    }


    val playVideoLiveData = MutableLiveData<NetworkRequest<ResponsePlayerVideo>>()


    fun callPlayVideoApi(id: Int) = viewModelScope.launch {


        playVideoLiveData.value = NetworkRequest.Loading()


        val response = repository.getPlayVideo(id)


        playVideoLiveData.value = NetworkResponse(response).generateResponse()


    }


    val popularActorLiveData = MutableLiveData<NetworkRequest<ResponseActor>>()


    fun callPopularActorApi(id: Int) = viewModelScope.launch {


        popularActorLiveData.value = NetworkRequest.Loading()


        val response = repository.getPopularActor(id)


        popularActorLiveData.value = NetworkResponse(response).generateResponse()


    }




    val getAllActorsLiveData = MutableLiveData<NetworkRequest<ResponseAllActors>>()


    fun callAllActorsApi(id: Int) = viewModelScope.launch {


        getAllActorsLiveData.value = NetworkRequest.Loading()


        val response = repository.getAllActorsOfFilm(id)


        getAllActorsLiveData.value = NetworkResponse(response).generateResponse()


    }







    fun deleteFavorite(entity: FavoriteEntity) = viewModelScope.launch {

        repository.deleteFavorite(entity)


    }


    fun addToFavorite(entity: FavoriteEntity) = viewModelScope.launch {

        repository.addToFavorite(entity)


    }

    val isProductInDatabaseLiveData = MutableLiveData<Boolean>()
    fun isInDatabaseOrNo(id: String) = viewModelScope.launch {


        val repository = repository.isInDatabase(id)

        repository.collect {


            isProductInDatabaseLiveData.value = it


        }


    }









}