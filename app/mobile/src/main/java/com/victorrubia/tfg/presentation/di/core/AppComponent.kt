package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.presentation.di.entry.EntrySubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.logged.LoggedSubComponent
import com.victorrubia.tfg.presentation.di.recover_password.RecoverPasswordSubComponent
import com.victorrubia.tfg.presentation.di.wear_service.WearServiceSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetModule::class,
    DataBaseModule::class,
    UseCaseModule::class,
    RepositoryModule::class,
    RemoteDataModule::class,
    LocalDataModule::class,
    CacheDataModule::class,
])
interface AppComponent {

    fun homeSubComponent(): HomeSubComponent.Factory
    fun loggedSubComponent(): LoggedSubComponent.Factory
    fun entrySubComponent() : EntrySubComponent.Factory
    fun recoverPasswordSubComponent() : RecoverPasswordSubComponent.Factory
    fun wearServiceSubComponent() : WearServiceSubComponent.Factory
}