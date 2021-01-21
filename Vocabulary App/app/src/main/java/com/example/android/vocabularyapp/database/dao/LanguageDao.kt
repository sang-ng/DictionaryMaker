package com.example.android.vocabularyapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.vocabularyapp.database.entities.LanguageDb

@Dao
interface LanguageDao {

    @Insert
    fun addLanguage(language : LanguageDb)

    @Update
    fun updateLanguage(language: LanguageDb)
}