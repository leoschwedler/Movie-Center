package com.example.moviesapp.api

import com.example.moviesapp.util.Const
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val currentUrl = chain.request().url()
        val newUrl = currentUrl.newBuilder()
        newUrl.addQueryParameter("api_key", Const.API_KEY)
        requestBuilder.url(newUrl.build())
        return chain.proceed(requestBuilder.build())
    }
}