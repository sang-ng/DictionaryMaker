package com.example.android.vocabularyapp.ui.addWord

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWordViewModel(private val repository: WordsRepository) : ViewModel() {


    val word: LiveData<Word>
        get() = _word

    val dataIsValid: LiveData<Boolean>
        get() = _dataIsValid

    private var _category = MutableLiveData<Category>()
    private var _word = MutableLiveData<Word>()
    private var _dataIsValid = MutableLiveData<Boolean>()


    fun addOrUpdate(name: String, translation: String) {

        if (categoryIsNotNull() && _word.value == null && inputNotEmpty(name, translation)) {

            addWord(name, translation)
            _dataIsValid.value = true

        } else if (categoryIsNotNull() && _word.value != null && inputNotEmpty(name, translation)) {

            updateWord(name, translation)
            _dataIsValid.value = true

        } else {
            _dataIsValid.value = false
        }
    }

    private fun inputNotEmpty(name: String, translation: String): Boolean {
        if (name.isNotEmpty() && translation.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun updateWord(newName: String, newTranslation: String) {
        viewModelScope.launch(Dispatchers.IO) {

            _word.value?.name = newName
            _word.value?.translation = newTranslation

            _word.value?.let { repository.updateWord(it) }
        }
    }

    private fun addWord(name: String, translation: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val word = _category.value?.id?.let { Word(0, name, translation, 0, it) }

            if (word != null) {
                repository.addWord(word)
            }
        }
    }

    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

    fun setSelectedWord(word: Word) {
        _word.value = word
    }

    private fun categoryIsNotNull(): Boolean = _category.value?.id != null

}