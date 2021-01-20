package com.example.android.vocabularyapp.model

import com.example.android.vocabularyapp.database.entities.LanguageDb

data class Language(
    val id: Long = 0,
    var name: String = "",
    var image: String? = ""
)

fun Language.toDatabaseModel(): LanguageDb {
    return LanguageDb(
        id = id,
        name = name,
        image = image
    )
}