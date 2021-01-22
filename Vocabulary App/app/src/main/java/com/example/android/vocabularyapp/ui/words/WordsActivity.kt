package com.example.android.vocabularyapp.ui.words

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import com.example.android.vocabularyapp.databinding.ActivityWordsBinding

class WordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}