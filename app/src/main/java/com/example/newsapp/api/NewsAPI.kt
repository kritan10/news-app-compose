package com.example.newsapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsAPI {
    @GET("/v1/latest-news/")
    @Headers("Authorization: YiA99v-t-viPwG6B5Z3XRmAR7wSY4JUjRqtxAnOMj1YyJcTL")
    fun getLatestNews(): Call<ApiResponse>
}