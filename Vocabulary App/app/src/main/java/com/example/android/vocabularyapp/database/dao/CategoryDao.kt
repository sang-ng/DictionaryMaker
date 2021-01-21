package com.example.android.vocabularyapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.example.android.vocabularyapp.database.entities.CategoryDb

@Dao
interface CategoryDao {

    @Insert
    fun addCategory(categoryDb: CategoryDb)

    @Update
    fun updateCategory(categoryDb: CategoryDb)
}