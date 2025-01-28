package com.example.movieapp.utils.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.repository.allmovies.AllTvListRepository
import javax.inject.Inject

class AllTvPagingSource @Inject constructor(private val repository: AllTvListRepository) :
    PagingSource<Int, ResponseMovies.Result>() {
    override fun getRefreshKey(state: PagingState<Int, ResponseMovies.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseMovies.Result> {
        return try {
            val currentPage = params.key ?: 1

            val response = repository.allTvList(currentPage)

            val data = response.body()?.results ?: emptyList()

            val responseData = mutableListOf<ResponseMovies.Result>()

            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )


        } catch (e: Exception) {

            LoadResult.Error(e)


        }
    }


}