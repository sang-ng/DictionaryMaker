package com.example.android.vocabularyapp.ui.addWord

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWordViewModel(private val repository: WordsRepository) : ViewModel() {


    private var _category = MutableLiveData<Category>()


    fun addWord(name: String, translation: String) {

        viewModelScope.launch(Dispatchers.IO) {

            if (categoryIsNotNull()) {

                val word = Word(0, name, translation, 0, _category.value!!.id)
                repository.addWord(word)
            }
        }
    }

    fun setSelectedCategory(category: Category) {
        _category.value = category
    }

    private fun categoryIsNotNull(): Boolean = _category.value?.id != null

}