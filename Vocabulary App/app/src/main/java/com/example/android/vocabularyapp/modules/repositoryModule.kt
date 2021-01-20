package com.example.android.vocabularyapp.modules

import com.example.android.vocabularyapp.repository.LanguageRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { LanguageRepository(dao = get()) }
}