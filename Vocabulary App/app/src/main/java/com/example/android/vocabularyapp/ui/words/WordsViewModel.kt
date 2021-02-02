package com.example.android.vocabularyapp.ui.words

import androidx.lifecycle.*
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.CategoryRepository
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordsViewModel(
    private val repoWord: WordsRepository,
    private val repoCategory: CategoryRepository
) : ViewModel(),
    DefaultLifecycleObserver {


    val wordsOfCategory: LiveData<List<Word>>
        get() = _wordsOfCategory

    val category: LiveData<Category>
        get() = _category

    private var _category = MutableLiveData<Category>()
    private var _wordsOfCategory = MutableLiveData<List<Word>>()

    init {
        getWordsOfCategory()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getWordsOfCategory()
        getAllCategories()
    }

    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

    private fun getWordsOfCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val words = _category.value?.id?.let { repoWord.getWordsOfCategory(it) }

            _wordsOfCategory.postValue(words)
        }
    }

    fun deleteCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            _category.value?.let {
                repoCategory.deleteCategory(it)
                repoWord.deleteWordsOfCategory(it.id)
            }
        }
    }

    fun deleteWord(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _wordsOfCategory.value?.get(position)?.let { repoWord.deleteWord(it) }
            getWordsOfCategory()
        }
    }

    fun updateCategory(newName: String) {
        viewModelScope.launch(Dispatchers.IO) {

            _category.value?.name = newName

            _category.value?.let { repoCategory.updateCategory(it) }
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repoCategory.getCategories()
        }
    }
}












