package com.example.android.vocabularyapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Category (
    val id: Long,
    var name : String,
    val languageId : Long
)
