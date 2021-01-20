package com.example.android.vocabularyapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "words")
data class WordDb constructor(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    var name : String,
    var translation : String,
    var image : String?,
    var goodWord : Boolean,
    var categoryId : Long,
){
}