package com.example.movieapp.utils.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.model.home.ResponseUpComing
import com.example.movieapp.data.repository.allmovies.AllPopularRepository
import com.example.movieapp.data.repository.allmovies.AllTvOnTheAirRepository
import com.example.movieapp.data.repository.allmovies.AllUpComingRepository
import javax.inject.Inject

class AllUpComingPagingSource@Inject constructor(private val repository: AllUpComingRepository):PagingSource<Int,ResponseUpComing.Result>() {
    override fun getRefreshKey(state: PagingState<Int, ResponseUpComing.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseUpComing.Result> {
        return try {



            val currentPage=params.key?:1

            val response=repository.allUpComing(currentPage)


            val data=response.body()?.results?: emptyList()





            val responseData= mutableListOf<ResponseUpComing.Result>()

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