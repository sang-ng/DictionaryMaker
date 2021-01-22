package com.example.android.vocabularyapp.ui.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import com.example.android.vocabularyapp.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordsViewModel(private val repository: WordsRepository) : ViewModel() {

//    val words = repository.words

    val words: LiveData<List<Word>>
        get() = _words

    val category: LiveData<Category>
        get() = _category

    private var _category = MutableLiveData<Category>()
    private var _words = MutableLiveData<List<Word>>()

    init {
        getWords()
    }

    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

    fun addWord(name: String, translation: String) {

        viewModelScope.launch(Dispatchers.IO) {

            if (categoryIsNotNull()) {

                val word = Word(0, name, translation, false, _category.value!!.id)
                repository.addWord(word)
            }
        }
    }

    private fun getWords() {
        viewModelScope.launch(Dispatchers.IO) {
            val words = _category.value?.id?.let { repository.getFilteredWords(it) }

            _words.postValue(words)
        }
    }

    private fun categoryIsNotNull(): Boolean = _category.value?.id != null
}