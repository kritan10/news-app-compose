package com.example.newsapp.data

import com.example.newsapp.data.daos.NewsDAO
import com.example.newsapp.data.models.News
import com.example.newsapp.data.models.NewsAndNote
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(private val newsDAO: NewsDAO) : NewsRepository {
    override fun getAllNews(): Flow<List<News>> {
        return newsDAO.getAll()
    }

    override suspend fun insertAll(news: Collection<News>) {
        newsDAO.insertAll(news)
    }

    override suspend fun delete(news: News) {
        newsDAO.delete(news)
    }

    override suspend fun getNewsById(id: String): News {
        return newsDAO.getNewsById(id)
    }

    override suspend fun nukeTable() {
        newsDAO.nukeTable()
    }

    override suspend fun getNewsAndNoteById(id: String): NewsAndNote {
        return newsDAO.getNewsAndNoteById(id)
    }

    override suspend fun toggleFavorite(news: News) {
        newsDAO.toggleFavorite(news)
    }

    override suspend fun getRowCount(): Int {
        return newsDAO.getRowCount()
    }

    override fun getFavourites(): Flow<List<News>> {
        return newsDAO.getFavourites()
    }

}