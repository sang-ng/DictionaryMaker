package com.example.android.vocabularyapp.ui.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.repository.WordsRepository

class LearnViewModel(private val repository: WordsRepository) : ViewModel(){

    val category: LiveData<Category>
        get() = _category

    private var _category = MutableLiveData<Category>()

    fun setSelectedCategory(category: Category) {
        _category.value = category
    }
}