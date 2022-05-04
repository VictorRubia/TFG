package com.victorrubia.tfg.presentation.measuring_menu

import android.app.PendingIntent.getActivity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.domain.usecase.EndActivityUseCase
import javax.inject.Inject


class MeasuringMenuViewModel(
    private val endActivityUseCase: EndActivityUseCase,
) : ViewModel(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var heartRateSensor : Sensor
    private lateinit var listaMediciones : MutableList<PPGMeasure>

    fun startMeasure(context : Context) {
        sensorManager = context.getSystemService(ComponentActivity.SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager.getDefaultSensor(65572)
        sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun endActivity() = liveData{
        stopSensor()
        val activity = endActivityUseCase.execute()
        emit(activity)
    }

    private fun stopSensor(){
        sensorManager.unregisterListener(this, heartRateSensor)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == 65572) {
            Log.d("MyTag", "Recibo Señal")
//            listaMediciones.add(PPGMeasure())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}