package com.example.android.vocabularyapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.model.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun getAllCategories() : LiveData<List<CategoryDb>>

    @Insert
    fun addCategory(categoryDb: CategoryDb)

    @Update
    fun updateCategory(categoryDb: CategoryDb)
}