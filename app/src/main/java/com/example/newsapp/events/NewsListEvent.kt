package com.example.newsapp.events

import com.example.newsapp.data.models.News

sealed class NewsListEvent {
    data class OnDeleteNewsClick(val news: News) : NewsListEvent()
    data class OnNewsClick(val news: News) : NewsListEvent()
    data class OnFavouriteClick(val news: News, val isFavourite: Boolean) : NewsListEvent()
    object OnFetchNewsRequest : NewsListEvent()
    object OnDeleteAllNewsClick : NewsListEvent()

//    data class AddNews(val news: News) : NewsListEvents()
//    data class AddNews(val news: News) : NewsListEvents()
//    data class AddNews(val news: News) : NewsListEvents()
//    data class AddNews(val news: News) : NewsListEvents()
}