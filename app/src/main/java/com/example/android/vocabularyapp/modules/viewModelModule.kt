package com.example.android.vocabularyapp.modules

import com.example.android.vocabularyapp.ui.addWord.AddWordViewModel
import com.example.android.vocabularyapp.ui.category.CategoryViewModel
import com.example.android.vocabularyapp.ui.learn.LearnActivity
import com.example.android.vocabularyapp.ui.learn.LearnViewModel
import com.example.android.vocabularyapp.ui.words.WordsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CategoryViewModel(repoCategory = get(), repoWords = get(), dispatchers = get()) }
    viewModel { WordsViewModel(repoWord = get(), repoCategory = get(), dispatchers = get()) }
    viewModel { AddWordViewModel(repository = get(), dispatchers = get()) }
    viewModel { LearnViewModel(repository = get(), dispatchers = get()) }
}