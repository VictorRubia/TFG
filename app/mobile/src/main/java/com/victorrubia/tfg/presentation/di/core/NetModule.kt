package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.api.TFGService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule(private val baseUrl : String) {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideTFGService(retrofit: Retrofit) : TFGService {
        return retrofit.create(TFGService::class.java)
    }
}