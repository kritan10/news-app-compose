package com.example.newsapp.data.models

import androidx.room.Embedded
import androidx.room.Relation


data class NewsAndNote(
    @Embedded val news: News,
    @Relation(
        parentColumn = "id",
        entityColumn = "newsId"
    )
    val note: Note?
)