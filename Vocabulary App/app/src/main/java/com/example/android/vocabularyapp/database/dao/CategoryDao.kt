package com.example.android.vocabularyapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.model.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories ORDER BY id DESC")
    fun getAllCategories(): LiveData<List<CategoryDb>>

    @Insert
    fun addCategory(categoryDb: CategoryDb)

    @Update
    fun updateCategory(categoryDb: CategoryDb)

    @Delete
    fun deleteCategory(categoryDb: CategoryDb)

    @Query("SELECT * FROM categories WHERE name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String) : LiveData<List<Category>>
}