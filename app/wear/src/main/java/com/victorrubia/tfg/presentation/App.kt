package com.victorrubia.tfg.presentation

import android.app.Application
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import com.victorrubia.tfg.BuildConfig
import com.victorrubia.tfg.data.model.user.User
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
            .remoteDataModule(RemoteDataModule(LocalDataModule().provideUserLocalDataSource(DataBaseModule().provideUserDao(DataBaseModule().provideActivityDataBase(applicationContext)))))
            .build()
    }

    fun getUser() : String {
        val user : User? = runBlocking { // this: CoroutineScope
                LocalDataModule().provideUserLocalDataSource(
                    DataBaseModule().provideUserDao(
                        DataBaseModule().provideActivityDataBase(applicationContext)
                    )
                ).getUserFromDB()
        }
        if(user != null) return user.apiKey else return ""
    }

    override fun createHomeSubComponent(): HomeSubComponent {
        return appComponent.homeSubComponent().create()
    }
}