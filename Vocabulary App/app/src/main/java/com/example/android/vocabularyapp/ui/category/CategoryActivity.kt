package com.example.android.vocabularyapp.ui.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.database.entities.LanguageDb
import com.example.android.vocabularyapp.databinding.ActivityCategoryBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryActivity : AppCompatActivity() {

    private val viewModel by viewModel<CategoryViewModel>()
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)

        initOnClick()

        setContentView(binding.root)

    }

    private fun initOnClick() {
        binding.floatingActionButton.setOnClickListener {
            val language = LanguageDb(0, "Deutsch", " -")

            viewModel.addLanguage(language)
        }
    }
}