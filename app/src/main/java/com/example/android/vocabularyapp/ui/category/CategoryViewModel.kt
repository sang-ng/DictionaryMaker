package com.example.android.vocabularyapp.ui.category

import androidx.lifecycle.*
import com.example.android.vocabularyapp.database.entities.CategoryDb
import com.example.android.vocabularyapp.repository.CategoryRepository
import com.example.android.vocabularyapp.repository.WordsRepository
import com.example.android.vocabularyapp.utils.DispatcherProvider
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repoCategory: CategoryRepository,
    repoWords: WordsRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel(), DefaultLifecycleObserver {

    val categories = repoCategory.categories
    val totalOfWords = repoWords.totalWords

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getCategories()
    }

    private fun getCategories() =
        viewModelScope.launch(dispatchers.io) {
            repoCategory.getCategories()
        }


    fun addCategory(categoryDb: CategoryDb) =
        viewModelScope.launch(dispatchers.io) {
            repoCategory.addCategory(categoryDb)
        }


    fun searchDatabase(searchQuery: String): LiveData<List<CategoryDb>> =
        repoCategory.searchDatabase(searchQuery)

}