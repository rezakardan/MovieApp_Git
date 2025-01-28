package com.example.movieapp.data.network

import com.example.movieapp.data.model.detail.ResponseActor
import com.example.movieapp.data.model.detail.ResponseAllActors
import com.example.movieapp.data.model.detail.ResponseDetail
import com.example.movieapp.data.model.detail.ResponsePlayerVideo
import com.example.movieapp.data.model.detail.ResponseSimilar
import com.example.movieapp.data.model.genre.ResponseGenreList
import com.example.movieapp.data.model.genre.ResponseMoviesListByGenre
import com.example.movieapp.data.model.home.ResponseMovies
import com.example.movieapp.data.model.home.ResponsePopular
import com.example.movieapp.data.model.home.ResponseTvOnTheAir
import com.example.movieapp.data.model.home.ResponseUpComing
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {


    @GET("movie/top_rated")
    suspend fun getTopRatedList(@Query("api_key") apiKey: String): Response<ResponsePopular>


    @GET("movie/popular")
    suspend fun getPopularList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<ResponsePopular>


    @GET("discover/tv")
    suspend fun tvList(@Query("api_key") apiKey: String): Response<ResponseMovies>


    @GET("tv/on_the_air")
    suspend fun tvOnTheAir(@Query("api_key") apiKey: String): Response<ResponseTvOnTheAir>


    @GET("movie/upcoming")
    suspend fun upComing(@Query("api_key") apiKey: String): Response<ResponseUpComing>


    @GET("movie/{id}")
    suspend fun movieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String

    ): Response<ResponseDetail>


    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Int, @Query("api_key") apiKey: String
    ): Response<ResponseSimilar>

    @GET("movie/{movie_id}/videos")
    suspend fun getPlayVideo(
        @Path("movie_id") id: Int, @Query("api_key") apiKey: String
    ): Response<ResponsePlayerVideo>


    // Actor
    @GET("person/{person_id}/movie_credits")
    suspend fun getPopularActor(
        @Path("person_id") id: Int, @Query("api_key") apiKey: String
    ): Response<ResponseActor>


    @GET("movie/{movie_id}/credits")

    suspend fun getActorsOfFilm(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<ResponseAllActors>


    // ResponseSearch
    @GET("search/movie")
    suspend fun searchByKeyword(
        @Query("query") keyWord: String, @Query("api_key") apiKey: String
    ): Response<ResponseSimilar>


    @GET("movie/top_rated")
    suspend fun getAllTopRatedList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<ResponsePopular>


    @GET("movie/popular")
    suspend fun getAllPopularList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<ResponsePopular>


    @GET("discover/tv")
    suspend fun allTvList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<ResponseMovies>


    @GET("tv/on_the_air")
    suspend fun allTvOnTheAir(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<ResponseTvOnTheAir>


    @GET("movie/upcoming")
    suspend fun allUpComing(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<ResponseUpComing>


    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): Response<ResponseGenreList>


    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): Response<ResponseMoviesListByGenre>

}