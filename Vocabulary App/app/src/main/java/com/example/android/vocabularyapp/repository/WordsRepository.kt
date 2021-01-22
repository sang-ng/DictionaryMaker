package com.example.android.vocabularyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.vocabularyapp.database.dao.WordDao
import com.example.android.vocabularyapp.database.entities.toDomainModel
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.model.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordsRepository(private val dao: WordDao) {

    val words: LiveData<List<Word>> = Transformations.map(dao.getWords()) {
        it.toDomainModel()
    }

    suspend fun addWord(word: Word) {
        withContext(Dispatchers.IO) {
            dao.addWord(word.toDatabaseModel())
        }
    }


}