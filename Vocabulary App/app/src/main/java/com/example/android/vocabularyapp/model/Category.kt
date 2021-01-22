package com.example.android.vocabularyapp.model

import android.os.Parcel
import android.os.Parcelable
import com.example.android.vocabularyapp.database.entities.CategoryDb
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: Long,
    var name: String
) : Parcelable

fun Category.toDatabaseModel(): CategoryDb {
    return CategoryDb(
        id = id,
        name = name
    )
}
