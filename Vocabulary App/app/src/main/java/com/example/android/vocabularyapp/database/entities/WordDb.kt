package com.example.android.vocabularyapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word


@Entity(tableName = "words")
data class WordDb constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name: String,
    var translation: String,
    var goodWord: Boolean,
    var categoryId: Long,
)

fun List<WordDb>.toDomainModel(): List<Word> {
    return map {
        Word(
            id = it.id,
            name = it.name,
            translation = it.translation,
            goodWord = it.goodWord,
            categoryId = it.categoryId
        )
    }
}

