package com.example.newsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.daos.NewsDAO
import com.example.newsapp.data.daos.NoteDAO
import com.example.newsapp.data.models.News
import com.example.newsapp.data.models.Note
import com.example.newsapp.data.utils.NewsConverter

//@Database(entities = [News::class], version = 1)
//@TypeConverters(NewsConverter::class)
//abstract class MyNewsRoomDatabase : RoomDatabase() {
//    abstract fun newsDao(): NewsDAO
//    abstract fun noteDao(): NoteDAO
//}

@Database(
    entities = [News::class, Note::class],
    version = 3,
)
@TypeConverters(NewsConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDAO
    abstract fun noteDao(): NoteDAO
}