package com.example.thegiftcherk

import androidx.multidex.MultiDexApplication
import com.example.thegiftcherk.setup.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appComponent)
        }
    }
}