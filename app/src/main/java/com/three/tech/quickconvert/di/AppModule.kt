package com.three.tech.quickconvert.di

import com.three.tech.quickconvert.ConverterService
import com.three.tech.quickconvert.networking.ConverterServiceImpl
import com.three.tech.quickconvert.networking.CurrencyConverterClient
import com.three.tech.quickconvert.networking.createHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideCurrencyConversion(): ConverterService {
        return ConverterServiceImpl(CurrencyConverterClient(createHttpClient(OkHttp.create())))
    }
}