package com.example.android.vocabularyapp.ui.words

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import com.example.android.vocabularyapp.databinding.ActivityWordsBinding
import com.example.android.vocabularyapp.model.Word
import com.example.android.vocabularyapp.ui.category.CategoryViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class WordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordsBinding
    private val viewModel by viewModel<WordsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsBinding.inflate(layoutInflater)

        initOnClick()
        setContentView(binding.root)
    }

    private fun initOnClick() {

        binding.wordsAddBtn.setOnClickListener {

            val word = Word(0, "Hallo", "Hola", true, 0)
            viewModel.addWord(word)
        }
    }
}