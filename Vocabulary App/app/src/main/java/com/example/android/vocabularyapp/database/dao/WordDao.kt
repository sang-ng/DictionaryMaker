package com.example.android.vocabularyapp.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.android.vocabularyapp.database.entities.WordDb

@Dao
interface WordDao {

    @Insert
    fun addWord(word: WordDb)

    @Update
    fun updateWord(word: WordDb)

    @Delete
    fun deleteWord(word: WordDb)

    @Query("SELECT * FROM words WHERE categoryId=:categoryId")
    fun getWordsOfCategory(categoryId: Long): List<WordDb>

    @Query("SELECT COUNT(*) FROM words")
    fun getTotalOfWords(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM words WHERE categoryId=:categoryId")
    fun getTotalWordsOfCategory(categoryId: Long): Int
}