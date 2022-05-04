package com.victorrubia.tfg.presentation

import android.app.Application
import com.victorrubia.tfg.BuildConfig
import com.victorrubia.tfg.data.db.UserDao
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfg.presentation.di.core.*
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.measuring_menu.MeasuringMenuSubComponent
import javax.inject.Inject

class App : Application(), Injector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule())
            .build()
    }

    override fun createHomeSubComponent(): HomeSubComponent {
        return appComponent.homeSubComponent().create()
    }

    override fun createActivityConfirmationSubComponent(): ActivityConfirmationSubComponent {
        return appComponent.activityConfirmationSubComponent().create()
    }

    override fun createMeasuringMenuSubComponent(): MeasuringMenuSubComponent {
        return appComponent.measuringMenuSubComponent().create()
    }
}