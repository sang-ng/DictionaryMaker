package com.example.android.vocabularyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.vocabularyapp.database.dao.WordDao
import com.example.android.vocabularyapp.database.entities.toDomainModel
import com.example.android.vocabularyapp.model.Category
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.model.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordsRepository(private val dao: WordDao) {

    val totalWords: LiveData<Int> = dao.getTotalOfWords()

    fun addWord(word: Word) =
        dao.addWord(word.toDatabaseModel())

    fun updateWord(word: Word) =
        dao.updateWord(word.toDatabaseModel())

    fun deleteWord(word: Word) =
        dao.deleteWord(word.toDatabaseModel())

    fun deleteWordsOfCategory(id: Long) =
        dao.deleteWordsOfCategory(id)

    fun getWordsOfCategory(categoryId: Long): List<Word> =
        dao.getWordsOfCategory(categoryId).toDomainModel()

}