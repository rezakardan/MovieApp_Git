package com.example.movieapp.utils.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.genre.ResponseMoviesListByGenre
import com.example.movieapp.data.network.ApiServices
import com.example.movieapp.utils.API_KEY
import javax.inject.Inject

class MoviesByGenrePagingSource @Inject constructor(private val apiServices: ApiServices,private val genreId:Int) :
    PagingSource<Int, ResponseMoviesListByGenre.Result>() {






    override fun getRefreshKey(state: PagingState<Int, ResponseMoviesListByGenre.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseMoviesListByGenre.Result> {

        val currentPage=params.key?:1





        return try {

            val response=apiServices.getMoviesByGenre(API_KEY,genreId,currentPage)

            val data=response.body()?.results!!

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage==1)null else currentPage-1,
                nextKey = if (data.isEmpty())null else  currentPage.plus(1)
            )




        }catch (e:Exception){
            LoadResult.Error(e)
        }






    }
}