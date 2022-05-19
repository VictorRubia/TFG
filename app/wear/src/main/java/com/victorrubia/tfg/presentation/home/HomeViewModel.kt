package com.victorrubia.tfg.presentation.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import com.victorrubia.tfg.domain.usecase.RequestUserUseCase
import com.victorrubia.tfg.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val newActivityUseCase: NewActivityUseCase,
    private val requestUserUseCase: RequestUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private lateinit var sensorManager: SensorManager
    var sensorStatus = mutableStateOf(false)

    fun loadingDelay() = liveData {
        kotlinx.coroutines.delay(15000)
        emit(true)
    }

    fun compatibility(context : Context) = liveData {
        var prueba : MutableLiveData<Boolean> = MutableLiveData(true)
        sensorManager = context.getSystemService(ComponentActivity.SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (currentSensor in sensorList) {
            if(currentSensor.stringType.contains("ppg") && !sensorStatus.value){
                sensorStatus.value = true
                prueba.value = false
            }
        }
        emit(prueba.value)
    }

    fun requestUser(){
        CoroutineScope(Dispatchers.IO).launch {
            requestUserUseCase.execute()
        }
    }

    fun saveUser(user : User){
        CoroutineScope(Dispatchers.IO).launch {
            saveUserUseCase.execute(user)
        }
    }

}