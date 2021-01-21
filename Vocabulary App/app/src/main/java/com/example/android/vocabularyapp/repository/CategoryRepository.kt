package com.example.android.vocabularyapp.repository

import com.example.android.vocabularyapp.database.dao.CategoryDao
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.database.entities.LanguageDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository(private val dao: CategoryDao) {

    suspend fun addCategory(category: CategoryDb) {
        withContext(Dispatchers.IO) {
            dao.addCategory(category)
        }
    }

    suspend fun updateCategory(category: CategoryDb) {
        withContext(Dispatchers.IO) {
            dao.updateCategory(category)
        }
    }
}