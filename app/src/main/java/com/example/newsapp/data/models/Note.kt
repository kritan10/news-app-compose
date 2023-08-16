package com.example.newsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    val newsId: String,
    var note: String,
    @PrimaryKey(autoGenerate = true) val noteId: Long = 0L,
)