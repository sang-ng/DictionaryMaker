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


    suspend fun addWord(word: Word) {
        withContext(Dispatchers.IO) {
            dao.addWord(word.toDatabaseModel())
        }
    }

    suspend fun updateWord(word: Word) {
        withContext(Dispatchers.IO) {
            dao.updateWord(word.toDatabaseModel())
        }
    }

    suspend fun deleteWord(word: Word) {
        withContext(Dispatchers.IO) {
            dao.deleteWord(word.toDatabaseModel())
        }
    }

    fun getWordsOfCategory(categoryId: Long): List<Word> {
        return dao.getWordsOfCategory(categoryId).toDomainModel()
    }


}