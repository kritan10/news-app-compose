package com.example.newsapp.data.utils

import com.example.newsapp.api.NewsRes
import com.example.newsapp.data.models.News
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewsMapper {
    fun newsResToNews(newsResponse: List<NewsRes>): List<News> {
        val newsList = mutableListOf<News>()
        newsResponse.forEach { newsRes ->
            newsList.add(
                News(
                    newsRes.id,
                    newsRes.title,
                    newsRes.description,
                    newsRes.url,
                    newsRes.author,
                    newsRes.image,
                    newsRes.language,
                    newsRes.category,
                    convertStringToDate(newsRes.published),
                    isFavourite = false
                )
            )
        }
        return newsList
    }

    private fun convertStringToDate(dateString: String): Date {
        val cleanedDate = dateString.substringBefore("+")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        return inputFormat.parse(cleanedDate) ?: Date()
    }
}