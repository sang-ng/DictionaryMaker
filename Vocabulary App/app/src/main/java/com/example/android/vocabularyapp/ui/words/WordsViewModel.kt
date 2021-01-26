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


    val words: LiveData<List<Word>>
        get() = _words

    val category: LiveData<Category>
        get() = _category

    private var _category = MutableLiveData<Category>()
    private var _words = MutableLiveData<List<Word>>()


    init {
        getWords()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getWords()
        getAllCategoris()
    }

    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

    private fun getWords() {
        viewModelScope.launch(Dispatchers.IO) {
            val words = _category.value?.id?.let { repoWord.getWordsOfCategory(it) }

            _words.postValue(words)
        }
    }

    fun deleteCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            _category.value?.let { repoCategory.deleteCategory(it) }
        }
    }

    //TODO: after updating category, name won't get updated automatically
    fun updateCategory(newName : String) {
        viewModelScope.launch(Dispatchers.IO) {

            _category.value?.name = newName

            _category.value?.let { repoCategory.updateCategory(it) }
        }
    }

    //TODO: check if list has one value left, if so delete it manually
    fun getAllCategoris() {
        viewModelScope.launch(Dispatchers.IO) {
            repoCategory.getCategories()
        }
    }
}












