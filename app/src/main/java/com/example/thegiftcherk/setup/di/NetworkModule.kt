package com.example.thegiftcherk.setup.di

import com.example.thegiftcherk.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.thegiftcherk.setup.network.VanadisInterceptor
import com.example.thegiftcherk.setup.network.VanadisService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {
    factory {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                when {
                    message.contains(" <-- END HTTP") -> println(" API-INFO-END: $message")
                    message.contains(" --> ") && !message.contains(" --> END") -> println(" API-INFO-REQUEST: $message")
                    message.contains(" <-- ") -> println(" API-INFO-CODE: $message")
                    message.contains(" [") -> println(" API-INFO-BODY: $message")
                    message.contains(" {") -> println(" API-INFO-BODY: $message")
                    message.contains(" (") -> println(" API-INFO-BODY: $message")
                }
            }
        }).apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    factory { VanadisInterceptor(prefs = get()) }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<VanadisInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>()).build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(BuildConfig.BASE_URL_VANADIS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory { get<Retrofit>().create<VanadisService>() }

}
