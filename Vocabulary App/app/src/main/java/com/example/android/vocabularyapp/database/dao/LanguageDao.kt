package com.example.android.vocabularyapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android.vocabularyapp.database.entities.LanguageDb

@Dao
interface LanguageDao {

    @Insert
    fun addLanguage(language : LanguageDb)
}