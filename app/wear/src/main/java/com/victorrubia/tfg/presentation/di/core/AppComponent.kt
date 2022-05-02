package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
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

    fun homeSubComponent() : HomeSubComponent.Factory

}