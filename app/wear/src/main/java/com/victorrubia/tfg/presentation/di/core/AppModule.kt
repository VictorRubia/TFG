package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import com.victorrubia.tfg.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.measuring_menu.MeasuringMenuSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    HomeSubComponent::class,
    ActivityConfirmationSubComponent::class,
    MeasuringMenuSubComponent::class
])
class AppModule(private val context : Context) {

    @Singleton
    @Provides
    fun provideApplicationContext() : Context {
        return context.applicationContext
    }

}