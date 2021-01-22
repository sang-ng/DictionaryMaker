package com.example.android.vocabularyapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.android.vocabularyapp.database.entities.WordDb

@Dao
interface WordDao {

    @Insert
    fun addWord(word: WordDb)
}