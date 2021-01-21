package com.example.android.vocabularyapp.repository

import com.example.android.vocabularyapp.database.dao.LanguageDao
import com.example.android.vocabularyapp.database.entities.LanguageDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LanguageRepository(private val dao: LanguageDao) {

    suspend fun addLanguage(language: LanguageDb) {
        withContext(Dispatchers.IO) {
            dao.addLanguage(language)
        }
    }

    suspend fun updateLanguage(language: LanguageDb) {
        withContext(Dispatchers.IO) {
            dao.updateLanguage(language)
        }
    }
}