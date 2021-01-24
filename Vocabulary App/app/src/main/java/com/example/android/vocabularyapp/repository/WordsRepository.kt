package com.example.android.vocabularyapp.repository

import com.example.android.vocabularyapp.database.dao.WordDao
import com.example.android.vocabularyapp.database.entities.toDomainModel
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.model.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordsRepository(private val dao: WordDao) {

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


    fun getWordsOfCategory(categoryId: Long): List<Word> {
        return dao.getWordsOfCategory(categoryId).toDomainModel()
    }

    fun getBadWordsOfCategory(categoryId: Long): List<Word> {
        return dao.getBadWordsOfCategory(categoryId).toDomainModel()
    }
}