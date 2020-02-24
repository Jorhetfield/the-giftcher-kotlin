package com.example.thegiftcherk.setup.di

import com.example.thegiftcherk.setup.Prefs
import org.koin.dsl.module

val preferencesModule = module {
    factory { Prefs(get()) }
}