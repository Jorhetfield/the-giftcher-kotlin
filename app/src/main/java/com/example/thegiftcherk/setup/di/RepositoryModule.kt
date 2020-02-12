package com.example.thegiftcherk.setup.di

import com.example.thegiftcherk.setup.network.VanadisRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { VanadisRepository(service = get(), context = get()) }
}