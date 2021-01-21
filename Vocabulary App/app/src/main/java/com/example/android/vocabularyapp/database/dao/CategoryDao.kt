package com.example.android.vocabularyapp.database.dao

import androidx.room.Insert
import androidx.room.Update
import com.example.android.vocabularyapp.database.entities.CategoryDb

interface CategoryDao {

    @Insert
    fun addCategory(categoryDb: CategoryDb)

    @Update
    fun updateCategory(categoryDb: CategoryDb)
}