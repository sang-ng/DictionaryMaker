package com.example.android.vocabularyapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class LanguageDb constructor(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    var name : String,
    var image : String?
)

//fun LanguageDb.toDomainModel():  {
//    return DepotDatabaseItem(
//        id = id,
//        title = title,
//        value = value,
//        valueIncrease = valueIncrease
//    )
//}
