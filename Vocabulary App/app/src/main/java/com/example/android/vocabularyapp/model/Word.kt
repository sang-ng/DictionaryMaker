package com.example.android.vocabularyapp.model

import com.example.android.vocabularyapp.database.entities.WordDb

class Word(
    val id: Long,
    var name: String,
    var translation: String,
    var goodWord: Boolean,
    var categoryId: Long,
)

fun Word.toDatabaseModel(): WordDb {
    return WordDb(
        id = id,
        name = name,
        translation = translation,
        goodWord = goodWord,
        categoryId = categoryId
    )
}
