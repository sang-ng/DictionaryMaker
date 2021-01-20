package com.example.android.vocabularyapp.modules

import com.example.android.vocabularyapp.ui.category.CategoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CategoryViewModel(repository = get()) }
}