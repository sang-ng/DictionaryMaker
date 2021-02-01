package com.example.android.vocabularyapp.ui.category

import androidx.lifecycle.*
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.repository.CategoryRepository
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CategoryViewModel(
    private val repoCategory: CategoryRepository,
    private val repoWords: WordsRepository
) : ViewModel(),
    DefaultLifecycleObserver {

    val categories = repoCategory.categories
    val totalOfWords = repoWords.totalWords


    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repoCategory.getCategories()
        }
    }

    fun addCategory(categoryDb: CategoryDb) {
        viewModelScope.launch(Dispatchers.IO) {
            repoCategory.addCategory(categoryDb)
        }
    }

    fun searchDatabase(searchQuery: String) : LiveData<List<Category>>{
        return repoCategory.searchDatabase(searchQuery)
    }
}