package com.example.notes.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
        val title: String,
        val note: String
        ) : java.io.Serializable{
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
}
