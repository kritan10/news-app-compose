package com.example.newsapp.data

import com.example.newsapp.data.models.News
import com.example.newsapp.data.models.NewsAndNote
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getAllNews(): Flow<List<News>>

    fun getFavourites(): Flow<List<News>>

    suspend fun insertAll(news: Collection<News>)

    suspend fun delete(news: News)

    suspend fun getNewsById(id: String): News

    suspend fun nukeTable()

    suspend fun getNewsAndNoteById(id: String): NewsAndNote

    suspend fun toggleFavorite(news: News)

    suspend fun getRowCount(): Int

}