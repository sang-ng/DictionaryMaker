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

    private var _category = MutableLiveData<Category>()
    private var _word = MutableLiveData<Word>()


    fun addOrUpdate(name: String, translation: String) {

        if (categoryIsNotNull() && _word.value == null) {

            addWord(name, translation)
        } else if (categoryIsNotNull() && _word.value != null) {

            updateWord(name, translation)
        }
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

            val word = Word(0, name, translation, 0, _category.value!!.id)
            repository.addWord(word)
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