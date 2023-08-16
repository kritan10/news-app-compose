package com.example.newsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "news_table_room")
data class News(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val url: String,
    val author: String,
    val image: String,
    val language: String,
    val category: List<String>,
    val published: Date,
    var isFavourite: Boolean
)


