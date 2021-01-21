package com.example.android.vocabularyapp.modules

import org.koin.dsl.module

val repositoryModule = module {
    single { LanguageRepository(dao = get()) }
}