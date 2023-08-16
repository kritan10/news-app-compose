package com.example.newsapp.sqlite

import android.content.Context
import android.util.Log
import com.example.newsapp.data.models.News

class SqliteService(context: Context) {
    private val mySqlite = MySqlite(context)

    fun fetchDataFromSqlite(): List<News> {
        return mySqlite.getAllNews()
    }

    fun fetchNewsById(id: String): News {
        return mySqlite.getNewsById(id)
    }

    fun isDatabaseEmpty(): Boolean {
        return mySqlite.isNewsTableEmpty()
    }

    fun saveDataToDatabase(data: List<News>?) {
        if (data != null) {
            mySqlite.addAllNews(data)
        } else {
            Log.d("EMPTY DATA", "Data set empty. Not added to db")
        }
    }

    fun deleteAllNewsFromTable() {
        mySqlite.deleteAllNews()
    }

    fun deleteById(id: String) {
        mySqlite.deleteNewsById(id)
    }
}