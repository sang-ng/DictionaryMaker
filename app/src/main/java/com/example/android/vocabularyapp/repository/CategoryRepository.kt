package com.example.android.vocabularyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Query
import com.example.android.vocabularyapp.database.dao.CategoryDao
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.database.entities.toDomainModel
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository(private val dao: CategoryDao) {

    val categories: LiveData<List<Category>> = Transformations.map(dao.getAllCategories()) {
        it.toDomainModel()
    }

    suspend fun getCategories(): LiveData<List<CategoryDb>> {
        return dao.getAllCategories()
    }

    fun addCategory(category: CategoryDb) {
            dao.addCategory(category)
    }

    fun updateCategory(category: Category) {
            dao.updateCategory(category.toDatabaseModel())
    }

    fun deleteCategory(category: Category) {
            dao.deleteCategory(category.toDatabaseModel())
    }

    fun searchDatabase(searchQuery: String): LiveData<List<CategoryDb>> {
        return dao.searchDatabase(searchQuery)
    }
}