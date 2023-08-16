package com.example.newsapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.models.Note

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Query("SELECT * FROM note_table WHERE newsId=:id")
    fun getNoteById(id: String): Note

    @Delete
    fun deleteNote(note: Note)
}