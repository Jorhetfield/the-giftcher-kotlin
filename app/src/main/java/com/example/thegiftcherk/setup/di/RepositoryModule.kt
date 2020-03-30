package com.example.thegiftcherk.setup.di

import com.example.thegiftcherk.setup.network.Repository
import org.koin.dsl.module

val repositoryModule = module {
    factory { Repository(service = get(), context = get()) }
}