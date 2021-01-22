package com.example.android.vocabularyapp.ui.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnViewModel(private val repository: WordsRepository) : ViewModel(){

    val category: LiveData<Category>
        get() = _category

    val words: LiveData<List<Word>>
        get() = _words

    private var _category = MutableLiveData<Category>()
    private var _words = MutableLiveData<List<Word>>()

    init {
        getWords()
    }

    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

    private fun getWords() {
        viewModelScope.launch(Dispatchers.IO) {
            val words = _category.value?.id?.let { repository.getFilteredWords(it) }

            words?.shuffled()

            _words.postValue(words)
        }
    }
}