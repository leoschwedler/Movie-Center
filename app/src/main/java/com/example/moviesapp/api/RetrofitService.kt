package com.example.moviesapp.api

import com.example.moviesapp.util.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {

    val okHttpClient = OkHttpClient.Builder()
        .writeTimeout(10,TimeUnit.SECONDS)
        .readTimeout(20,TimeUnit.SECONDS)
        .connectTimeout(20,TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build()

    val theMovieDB = Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(TheMovieDBAPI::class.java)
}