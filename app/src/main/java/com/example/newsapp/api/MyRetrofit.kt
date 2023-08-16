package com.example.newsapp.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.suspendCoroutine

class MyRetrofit {
    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.currentsapi.services/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    suspend fun getLatestNews() =
        suspendCoroutine { it ->
            retrofit.create(NewsAPI::class.java).getLatestNews().enqueue(
                object : Callback<ApiResponse> {
                    val TAG = "NewsAPI"
                    override fun onResponse(
                        call: Call<ApiResponse>,
                        response: retrofit2.Response<ApiResponse>
                    ) {
                        Log.d(TAG, "Response received.")
                        Log.d(TAG, Thread.currentThread().name)
                        Log.d(TAG, "Save to DB started")
                        //Log.d(TAG, response.body().toString())
                        it.resumeWith(Result.success(response.body()!!))
                        Log.d(TAG, "Save to DB end")

                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.d(TAG, t.stackTraceToString())
                        it.resumeWith(Result.failure(t))
                    }
                }
            )
        }
}
