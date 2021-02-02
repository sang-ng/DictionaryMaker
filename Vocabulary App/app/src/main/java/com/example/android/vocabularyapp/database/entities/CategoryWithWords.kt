package com.example.android.vocabularyapp.database.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity
data class CategoryWithWords(
    @Embedded val category: CategoryDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val words: List<WordDb>
)