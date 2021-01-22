package com.example.android.vocabularyapp.ui.words

import androidx.lifecycle.*
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordsViewModel(private val repository: WordsRepository) : ViewModel(), DefaultLifecycleObserver {

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
    }

    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

    private fun getWords() {
        viewModelScope.launch(Dispatchers.IO) {
            val words = _category.value?.id?.let { repository.getFilteredWords(it) }

            _words.postValue(words)
        }
    }


    private fun categoryIsNotNull(): Boolean = _category.value?.id != null
}