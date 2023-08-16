package com.example.newsapp.sqlite


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.newsapp.data.models.News
import com.example.newsapp.sqlite.SqlHelper.CREATE_NEWS_TABLE
import com.example.newsapp.sqlite.SqlHelper.DB_NAME
import com.example.newsapp.sqlite.SqlHelper.DB_VERSION
import com.example.newsapp.sqlite.SqlHelper.DELETE_NEWS_TABLE
import com.example.newsapp.sqlite.SqlHelper.NEWS_TABLE
import com.example.newsapp.sqlite.SqlHelper.NewsColumn
import java.util.Date

class MySqlite(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private fun getNews(id: String?): List<News> {
        val db = readableDatabase
        val newsList = mutableListOf<News>()
        val selection = if (id != null) "${BaseColumns._ID} = ?" else null
        val selectionArgs = if (id != null) arrayOf(id) else null

        val cursor = db.query(
            NEWS_TABLE,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        with(cursor) {
            if (moveToFirst()) {
                do {
                    val newsId = getString(getColumnIndexOrThrow(BaseColumns._ID))
                    val title = getString(getColumnIndexOrThrow(NewsColumn.TITLE))
                    val description = getString(getColumnIndexOrThrow(NewsColumn.DESCRIPTION))
                    val author = getString(getColumnIndexOrThrow(NewsColumn.AUTHOR))
                    val image = getString(getColumnIndexOrThrow(NewsColumn.IMAGE))
                    val language = getString(getColumnIndexOrThrow(NewsColumn.LANGUAGE))
                    val url = getString(getColumnIndexOrThrow(NewsColumn.URL))
                    val category = getString(getColumnIndexOrThrow(NewsColumn.CATEGORY))
                    val published = getString(getColumnIndexOrThrow(NewsColumn.PUBLISHED_DATE))

                    newsList.add(
                        News(
                            newsId,
                            title,
                            description,
                            url,
                            author,
                            image,
                            language,
                            listOf<String>(category),
                            Date(),
                            false
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return newsList
        }
    }

    private fun addNews(news: News) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(BaseColumns._ID, news.id)
            put(NewsColumn.TITLE, news.title)
            put(NewsColumn.DESCRIPTION, news.description)
            put(NewsColumn.AUTHOR, news.author)
            put(NewsColumn.IMAGE, news.image)
            put(NewsColumn.CATEGORY, news.category[0])
            put(NewsColumn.PUBLISHED_DATE, 1L)
        }
        db.insertWithOnConflict(NEWS_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun addAllNews(newsList: Collection<News>) {
        newsList.forEach {
            addNews(it)
        }
    }

    fun getAllNews(): List<News> {
        return getNews(null)
    }

    fun getNewsById(id: String): News {
        return getNews(id).first()
    }

    fun isNewsTableEmpty(): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $NEWS_TABLE", null)
        var isEmpty = false
        with(cursor) {
            moveToFirst()
            Log.v("SQLITE", getInt(0).toString())
            if (getInt(0) == 0) {
                Log.v("SQLITE", "reached in if")
                isEmpty = true
            }
        }
        cursor.close()
        db.close()
        return isEmpty
    }

    fun deleteNewsById(id: String) {
        val db = writableDatabase
        db.delete(NEWS_TABLE, "${BaseColumns._ID} LIKE ?", arrayOf(id))
        db.close()
    }

    fun deleteAllNews() {
        val db = writableDatabase
        db.delete(NEWS_TABLE, null, null)
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_NEWS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_NEWS_TABLE)
        onCreate(db)
    }
}