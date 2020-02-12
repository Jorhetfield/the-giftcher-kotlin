package es.vanadis.utg_estaxi_profesional.setup.di

import es.vanadis.utg_estaxi_profesional.setup.Prefs
import org.koin.dsl.module

val preferencesModule = module {
    factory { Prefs(get()) }
}