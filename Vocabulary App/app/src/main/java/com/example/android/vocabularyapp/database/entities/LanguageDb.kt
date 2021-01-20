package com.example.android.vocabularyapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.vocabularyapp.model.Language

@Entity(tableName = "languages")
data class LanguageDb constructor(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    var name : String,
    var image : String?
)

fun LanguageDb.toDomainModel(): Language {
    return Language(
        id = id,
        name = name,
        image = image,
    )
}
