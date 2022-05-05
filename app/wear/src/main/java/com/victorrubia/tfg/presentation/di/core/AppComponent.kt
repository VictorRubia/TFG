package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfg.presentation.di.feelings_menu.FeelingsMenuSubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.measuring_menu.MeasuringMenuSubComponent
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
    fun activityConfirmationSubComponent() : ActivityConfirmationSubComponent.Factory
    fun measuringMenuSubComponent() : MeasuringMenuSubComponent.Factory
    fun feelingsMenuSubComponent() : FeelingsMenuSubComponent.Factory

}