package com.example.android.vocabularyapp.modules

import com.example.android.vocabularyapp.database.VocDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single { VocDatabase.getDatabase(androidApplication()) }
    single { VocDatabase.getDatabase(androidApplication()).categoryDao }
    single { VocDatabase.getDatabase(androidContext()).wordDao }

}