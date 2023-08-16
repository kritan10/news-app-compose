package com.example.newsapp.data

import android.content.Context
import androidx.room.Room

class RoomDBService(context: Context) {
    private val roomDB: NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_room_db"
        ).fallbackToDestructiveMigration()
            .build()

    fun getInstance(): NewsDatabase {
        return roomDB
    }

}