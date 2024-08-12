package com.example.moviesapp.api

import com.example.moviesapp.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBAPI {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int) :Response<MoviesResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int) :Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int) :Response<MoviesResponse>

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): Response<MoviesResponse>


}