package com.example.android.vocabularyapp.modules

import com.example.android.vocabularyapp.repository.CategoryRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CategoryRepository(dao = get()) }
}