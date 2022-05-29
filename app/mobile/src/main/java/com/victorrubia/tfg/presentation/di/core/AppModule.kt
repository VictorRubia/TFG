package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import com.victorrubia.tfg.presentation.di.entry.EntrySubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.logged.LoggedSubComponent
import com.victorrubia.tfg.presentation.di.recover_password.RecoverPasswordSubComponent
import com.victorrubia.tfg.presentation.di.wear_service.WearServiceSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger [AppModule] that provides objects which will live during the application lifecycle.
 */
@Module(subcomponents = [
    HomeSubComponent::class,
    LoggedSubComponent::class,
    EntrySubComponent::class,
    RecoverPasswordSubComponent::class,
    WearServiceSubComponent::class
])
class AppModule(private val context: Context) {

    /**
     * Provides the application [Context].
     * @return the application [Context].
     * Singleton annotation ensures that the object is created only once.
     */
    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return context.applicationContext
    }

}