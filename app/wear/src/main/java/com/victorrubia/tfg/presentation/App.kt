package com.victorrubia.tfg.presentation

import android.app.Application
import com.victorrubia.tfg.BuildConfig
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.di.core.*
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent

class App : Application(), Injector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule("Bearer " + "a849134f7fedc8031baeb4fee18b33d2"))
            .cacheDataModule(CacheDataModule())
            .localDataModule(LocalDataModule())
            .build()
    }

    override fun createHomeSubComponent(): HomeSubComponent {
        return appComponent.homeSubComponent().create()
    }
}