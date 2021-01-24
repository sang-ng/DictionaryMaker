package com.example.android.vocabularyapp.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    val categories = repository.categories

    fun addCategory(categoryDb: CategoryDb) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCategory(categoryDb)
        }
    }
}