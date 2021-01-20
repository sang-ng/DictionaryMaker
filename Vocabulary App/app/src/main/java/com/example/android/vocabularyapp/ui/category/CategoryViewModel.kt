package com.example.android.vocabularyapp.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.vocabularyapp.database.entities.LanguageDb
import com.example.android.vocabularyapp.repository.LanguageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: LanguageRepository) : ViewModel() {

    fun addLanguage(language: LanguageDb) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLanguage(language)
        }
    }
}