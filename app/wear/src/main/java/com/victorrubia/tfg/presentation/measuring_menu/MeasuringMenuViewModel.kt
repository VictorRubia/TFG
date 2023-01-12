package com.victorrubia.tfg.presentation.measuring_menu

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
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

/**
 * [ViewModel] for the [MeasuringMenuActivity]
 *
 * @property endActivityUseCase [EndActivityUseCase] to end the current activity
 * @property getCurrentActivityUseCase [GetCurrentActivityUseCase] to get the current activity
 * @property savePPGMeasureUseCase [SavePPGMeasureUseCase] to save a new PPG measure
 * @property endPPGMeasureUseCase [EndPPGMeasureUseCase] to end the current PPG measure
 */
class MeasuringMenuViewModel(
    private val endActivityUseCase: EndActivityUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
    private val savePPGMeasureUseCase: SavePPGMeasureUseCase,
    private val endPPGMeasureUseCase: EndPPGMeasureUseCase
) : ViewModel(), SensorEventListener {

    // Sesor manager to get the PPG sensor
    private lateinit var sensorManager: SensorManager
    private lateinit var heartRateSensor : Sensor
    var internetStatus = mutableStateOf(true)
    var sensorStatus = true
    var ppgSensorId : Int = 0


    /**
     * Gets the PPG Sensor from the device. Iterates all over the device's
     * sensors to find the PPG sensor
     *
     * @param context [Context] of the application
     */
    fun startMeasure(context : Context) {
        sensorManager = context.getSystemService(ComponentActivity.SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (currentSensor in sensorList) {
            if(currentSensor.stringType.contains("ppg") && sensorStatus){
                ppgSensorId = currentSensor.type
//                heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
                heartRateSensor = sensorManager.getDefaultSensor(ppgSensorId)
                sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL)
                sensorStatus = false
            }
        }
    }

    /**
     * Unregisters the PPG sensor, ends the current activity and ends the current PPG measure.
     */
    fun endActivity() = liveData{
        stopSensor()
        getCurrentActivityUseCase.execute()?.let { endPPGMeasureUseCase.execute(it.id) }
        val activity = endActivityUseCase.execute()
        emit(activity)
    }

    /**
     * Stops the PPG sensor
     */
    private fun stopSensor(){
        sensorManager.unregisterListener(this, heartRateSensor)
    }

    /**
     * When we register a sensor event for the type of sensor we are looking for (PPG),
     * we execute the use case to save it.
     *
     * @param event [SensorEvent] of the sensor
     */
    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == ppgSensorId) {
            viewModelScope.launch{
                getCurrentActivityUseCase.execute()
                    ?.let { internetStatus.value = savePPGMeasureUseCase.execute(PPGMeasure(event.values[0].toInt(), Date()), it.id).value }
            }
        }
    }

    /**
     * Not used
     */
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}