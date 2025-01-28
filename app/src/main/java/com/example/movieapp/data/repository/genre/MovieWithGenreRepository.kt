package com.example.movieapp.data.repository.genre

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.movieapp.data.db.FavoriteDao
import com.example.movieapp.data.db.FavoriteEntity
import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import com.example.movieapp.utils.paging.MoviesByGenrePagingSource
import retrofit2.http.Query
import javax.inject.Inject

class MovieWithGenreRepository @Inject constructor(private val api: ApiServices) {


    fun getMoviesByGenre(genreId: Int) =
        Pager(config = PagingConfig(pageSize = 20, maxSize = 300, enablePlaceholders = false),
            pagingSourceFactory = { MoviesByGenrePagingSource(api, genreId) }).liveData


}