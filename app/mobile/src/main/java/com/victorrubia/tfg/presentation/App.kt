package com.victorrubia.tfg.presentation

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.victorrubia.tfg.BuildConfig
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.di.core.*
import com.victorrubia.tfg.presentation.di.entry.EntrySubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.logged.LoggedSubComponent
import com.victorrubia.tfg.presentation.di.recover_password.RecoverPasswordSubComponent
import com.victorrubia.tfg.presentation.di.wear_service.WearServiceSubComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class App : Application(), Injector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule())
            .cacheDataModule(CacheDataModule())
            .localDataModule(LocalDataModule())
            .build()
    }

    override fun createHomeSubComponent(): HomeSubComponent {
        return appComponent.homeSubComponent().create()
    }

    override fun createLoggedSubComponent(): LoggedSubComponent{
        return appComponent.loggedSubComponent().create()
    }

    override fun createEntrySubComponent(): EntrySubComponent {
        return appComponent.entrySubComponent().create()
    }

    override fun createRecoverPasswordSubComponent(): RecoverPasswordSubComponent {
        return appComponent.recoverPasswordSubComponent().create()
    }

    override fun createWearServiceSubComponent(): WearServiceSubComponent {
        return appComponent.wearServiceSubComponent().create()
    }
}