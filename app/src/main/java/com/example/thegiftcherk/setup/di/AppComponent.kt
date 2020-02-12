package com.example.thegiftcherk.setup.di

import es.vanadis.utg_estaxi_profesional.setup.di.preferencesModule
import es.vanadis.utg_estaxi_profesional.setup.di.repositoryModule

val appComponent = listOf(networkModule, repositoryModule, preferencesModule)
