package com.example.moviesapp.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int,
    @SerializedName("results")
    val Movies: List<Movies>,
    val total_pages: Int,
    val total_results: Int
)