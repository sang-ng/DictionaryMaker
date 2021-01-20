package com.example.android.vocabularyapp.ui.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.database.entities.LanguageDb
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryActivity : AppCompatActivity() {

    private val viewModel by viewModel<CategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        val language = LanguageDb(0, "Deuscth", " -")

        viewModel.addLanguage(language)
    }
}