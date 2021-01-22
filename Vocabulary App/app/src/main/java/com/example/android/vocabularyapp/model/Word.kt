package com.example.android.vocabularyapp.model

class Word(
    val id: Long,
    var name: String,
    var translation: String,
    var goodWord: Boolean,
    var categoryId: Long,
)