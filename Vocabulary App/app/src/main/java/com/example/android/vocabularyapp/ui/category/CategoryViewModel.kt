package com.example.android.vocabularyapp.ui.category

import androidx.lifecycle.*
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel(),
    DefaultLifecycleObserver {

    //TODO: category list will be updated after deleting category, but only if there are more than one item.

    val categories = repository.categories

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories()
        }
    }

    fun addCategory(categoryDb: CategoryDb) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCategory(categoryDb)
        }
    }
}