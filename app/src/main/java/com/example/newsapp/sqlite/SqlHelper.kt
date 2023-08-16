package com.example.newsapp.sqlite

import android.provider.BaseColumns

object SqlHelper {
    const val DB_NAME = "news_db"
    const val DB_VERSION = 1
    const val NEWS_TABLE = "news_table"

    object NewsColumn : BaseColumns {
        const val TITLE = "TITLE"
        const val DESCRIPTION = "DESCRIPTION"
        const val AUTHOR = "AUTHOR"
        const val IMAGE = "IMAGE"
        const val CATEGORY = "CATEGORY"
        const val PUBLISHED_DATE = "PUBLISHED_DATE"
        const val LANGUAGE = "LANGUAGE"
        const val URL = "URL"
    }

    const val CREATE_NEWS_TABLE = """
       CREATE TABLE $NEWS_TABLE (
       ${BaseColumns._ID} TEXT PRIMARY KEY,
       ${NewsColumn.TITLE} TEXT,
       ${NewsColumn.DESCRIPTION} TEXT,
       ${NewsColumn.AUTHOR} TEXT,
       ${NewsColumn.IMAGE} TEXT,
       ${NewsColumn.CATEGORY} TEXT,
       ${NewsColumn.PUBLISHED_DATE} TEXT,
       ${NewsColumn.URL} TEXT DEFAULT 'NO-URL',
       ${NewsColumn.LANGUAGE} TEXT DEFAULT 'EN'
       );
    """

    const val DELETE_NEWS_TABLE = "DROP TABLE IF EXISTS $NEWS_TABLE"

}