package com.example.android.vocabularyapp.modules

import com.example.android.vocabularyapp.repository.CategoryRepository
import com.example.android.vocabularyapp.repository.WordsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CategoryRepository(dao = get()) }
    single { WordsRepository(dao = get()) }
}