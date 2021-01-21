package com.example.android.vocabularyapp.model

import com.example.android.vocabularyapp.database.entities.CategoryDb

data class Category(
    val id: Long,
    var name: String
)

fun Category.toDatabaseModel(): CategoryDb {
    return CategoryDb(
        id = id,
        name = name
    )
}
