package com.victorrubia.tfg.presentation.measuring_menu

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.domain.usecase.EndActivityUseCase
import com.victorrubia.tfg.domain.usecase.EndPPGMeasureUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfg.domain.usecase.SavePPGMeasureUseCase
import kotlinx.coroutines.launch
import java.util.*

class MeasuringMenuViewModel(
    private val endActivityUseCase: EndActivityUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
    private val savePPGMeasureUseCase: SavePPGMeasureUseCase,
    private val endPPGMeasureUseCase: EndPPGMeasureUseCase
) : ViewModel(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var heartRateSensor : Sensor
    var internetStatus = mutableStateOf(true)
    var sensorStatus = true
    var ppgSensorId : Int = 0


    fun startMeasure(context : Context) {
        sensorManager = context.getSystemService(ComponentActivity.SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (currentSensor in sensorList) {
            if(currentSensor.stringType.contains("ppg") && !sensorStatus){
                ppgSensorId = currentSensor.type
                heartRateSensor = sensorManager.getDefaultSensor(ppgSensorId)
                sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL)
                sensorStatus = false
            }
        }
    }

    fun endActivity() = liveData{
        stopSensor()
        getCurrentActivityUseCase.execute()?.let { endPPGMeasureUseCase.execute(it.id) }
        val activity = endActivityUseCase.execute()
        emit(activity)
    }

    private fun stopSensor(){
        sensorManager.unregisterListener(this, heartRateSensor)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == 65572) {
            viewModelScope.launch{
                getCurrentActivityUseCase.execute()
                    ?.let { internetStatus.value = savePPGMeasureUseCase.execute(PPGMeasure(event.values[0].toInt(), Date()), it.id).value }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}