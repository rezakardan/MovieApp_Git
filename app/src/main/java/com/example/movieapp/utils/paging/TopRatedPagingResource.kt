package com.example.movieapp.utils.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.repository.allmovies.AllMoviesRepository
import javax.inject.Inject

class TopRatedPagingResource @Inject constructor(private val repository: AllMoviesRepository) :
    PagingSource<Int, ResponsePopular.Result>() {
    override fun getRefreshKey(state: PagingState<Int, ResponsePopular.Result>): Int? {

        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponsePopular.Result> {



        return try {

          val currentPage=params.key?:1

            val response=repository.getAllTopRatedList(currentPage)
            val data=response.body()?.results?: emptyList()

            val responseData= mutableListOf<ResponsePopular.Result>()

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