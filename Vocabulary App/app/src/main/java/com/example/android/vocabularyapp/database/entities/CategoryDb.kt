package com.example.android.vocabularyapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class CategoryDb constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name : String
)
