package com.example.movieapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapp.data.repository.genre.MovieWithGenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesByGenreViewModel @Inject constructor(
    private val repository: MovieWithGenreRepository,
  val state: SavedStateHandle
) : ViewModel() {



















        private val currentData=state.getLiveData<String>(CURRENT_QUERY,DEFAULT_QUERY)


    val moviesByGenre=currentData.switchMap { id->


        val genreId = id.toIntOrNull() ?: 0
        repository.getMoviesByGenre(genreId).cachedIn(viewModelScope)



    }


    fun updateMoviesByGenre(id:String){

        currentData.value=id




    }








companion object{

    private const val CURRENT_QUERY="current_query"


    private const val DEFAULT_QUERY=""



}

}