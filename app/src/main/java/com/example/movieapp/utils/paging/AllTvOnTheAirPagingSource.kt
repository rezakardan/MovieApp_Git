package com.example.movieapp.utils.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.model.home.ResponseTvOnTheAir
import com.example.movieapp.data.repository.allmovies.AllPopularRepository
import com.example.movieapp.data.repository.allmovies.AllTvOnTheAirRepository
import javax.inject.Inject

class AllTvOnTheAirPagingSource@Inject constructor(private val repository: AllTvOnTheAirRepository):PagingSource<Int,ResponseTvOnTheAir.Result>() {
    override fun getRefreshKey(state: PagingState<Int, ResponseTvOnTheAir.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseTvOnTheAir.Result> {
        return try {



            val currentPage=params.key?:1

            val response=repository.allTvOnTheAir(currentPage)


            val data=response.body()?.results?: emptyList()





            val responseData= mutableListOf<ResponseTvOnTheAir.Result>()

            responseData.addAll(data)



            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage==1)null else -1,
                nextKey = currentPage.plus(1)
            )


        }catch (e:Exception){

            LoadResult.Error(e)
        }

    }

}