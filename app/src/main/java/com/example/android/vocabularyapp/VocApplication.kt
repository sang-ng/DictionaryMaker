package com.example.android.vocabularyapp

import android.app.Application
import com.example.android.vocabularyapp.modules.databaseModule
import com.example.android.vocabularyapp.modules.dispatcherModule
import com.example.android.vocabularyapp.modules.repositoryModule
import com.example.android.vocabularyapp.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VocApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@VocApplication)
            modules(databaseModule, repositoryModule, viewModelModule, dispatcherModule)
        }
    }
}