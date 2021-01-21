package com.example.android.vocabularyapp.repository

import androidx.lifecycle.LiveData
import com.example.android.vocabularyapp.database.dao.CategoryDao
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository(private val dao: CategoryDao) {

    suspend fun addCategory(category: CategoryDb) {
        withContext(Dispatchers.IO) {
            dao.addCategory(category)
        }
    }

    fun getCategory(id: Long): LiveData<Category> {
        return dao.getCategoryById(id)
    }

    suspend fun updateCategory(category: Category) {
        withContext(Dispatchers.IO) {
            dao.updateCategory(category.toDatabaseModel())
        }
    }
}