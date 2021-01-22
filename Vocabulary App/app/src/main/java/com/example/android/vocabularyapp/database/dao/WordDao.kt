package com.example.android.vocabularyapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android.vocabularyapp.database.entities.WordDb

@Dao
interface WordDao {

    @Insert
    fun addWord(word: WordDb)

    @Query("SELECT * FROM words")
    fun getWords() : LiveData<List<WordDb>>
}