package com.example.android.vocabularyapp.repository

import com.example.android.vocabularyapp.database.dao.LanguageDao
import com.example.android.vocabularyapp.database.entities.LanguageDb

class LanguageRepository(private val dao: LanguageDao) {

    fun addLanguage(language: LanguageDb) {
        dao.addLanguage(language)
    }
}