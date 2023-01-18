package com.example.notes.db

import androidx.room.*
import com.example.notes.db.entities.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getAllNotes() : List<Note>

    @Update
    suspend fun updateNote(note: Note)

    @Insert
    suspend fun addMultipleNotes(vararg note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}