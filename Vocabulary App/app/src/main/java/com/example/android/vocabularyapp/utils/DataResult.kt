package com.example.android.vocabularyapp.utils

import com.example.android.vocabularyapp.database.entities.WordDb
import java.lang.Exception

sealed class DataResult {
    data class Success(val words: List<WordDb>) : DataResult()
    data class Failure(val exception: Exception) : DataResult()
}
