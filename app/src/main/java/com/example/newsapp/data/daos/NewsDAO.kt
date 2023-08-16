package com.example.newsapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.newsapp.data.models.News
import com.example.newsapp.data.models.NewsAndNote
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: Collection<News>)

    @Delete
    suspend fun delete(news: News)

    @Query("SELECT * FROM news_table_room ORDER BY published DESC")
    fun getAll(): Flow<List<News>>

    @Query("SELECT * FROM news_table_room WHERE id=:id ")
    suspend fun getNewsById(id: String): News

    @Query("DELETE FROM news_table_room")
    suspend fun nukeTable()

    @Transaction
    @Query("SELECT * FROM news_table_room WHERE id=:id")
    suspend fun getNewsAndNoteById(id: String): NewsAndNote

    @Update
    suspend fun toggleFavorite(news: News)

    @Query("SELECT COUNT(*) FROM news_table_room")
    suspend fun getRowCount(): Int

    @Query("SELECT * FROM news_table_room WHERE isFavourite=1")
    fun getFavourites(): Flow<List<News>>
}