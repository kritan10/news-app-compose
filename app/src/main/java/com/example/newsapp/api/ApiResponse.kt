package com.example.newsapp.api

data class ApiResponse(val status: String, val news: List<NewsRes>)

data class NewsRes(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val author: String,
    val image: String,
    val language: String,
    val category: List<String>,
    val published: String,
)
