package com.example.android.vocabularyapp.ui.words

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordsViewModel(private val repository: WordsRepository) : ViewModel() {

    val category = MutableLiveData<Category>()


    fun addWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWord(word)
        }
    }

    fun setSelectedCategory(category: Category) {
        this.category.value = category
    }
}