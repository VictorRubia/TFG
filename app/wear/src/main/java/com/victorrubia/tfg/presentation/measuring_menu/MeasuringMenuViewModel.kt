package com.victorrubia.tfg.presentation.measuring_menu

import android.app.PendingIntent.getActivity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.domain.usecase.EndActivityUseCase
import com.victorrubia.tfg.domain.usecase.EndPPGMeasureUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfg.domain.usecase.SavePPGMeasureUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.util.*
import javax.inject.Inject


class MeasuringMenuViewModel(
    private val endActivityUseCase: EndActivityUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
    private val savePPGMeasureUseCase: SavePPGMeasureUseCase,
    private val endPPGMeasureUseCase: EndPPGMeasureUseCase
) : ViewModel(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var heartRateSensor : Sensor
    var internetStatus = mutableStateOf(true)


    fun startMeasure(context : Context) {
        sensorManager = context.getSystemService(ComponentActivity.SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager.getDefaultSensor(65572)
        sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL)
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