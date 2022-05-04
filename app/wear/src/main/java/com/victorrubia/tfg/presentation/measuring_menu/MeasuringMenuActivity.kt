package com.victorrubia.tfg.presentation.measuring_menu

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.StopCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.material.*
import com.google.android.gms.wearable.Wearable
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.start_menu.StartMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme
import javax.inject.Inject

class MeasuringMenuActivity:  ComponentActivity() {

    @Inject
    lateinit var factory: MeasuringMenuViewModelFactory
    private lateinit var measuringMenuViewModel: MeasuringMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        (application as Injector).createMeasuringMenuSubComponent()
            .inject(this)
        measuringMenuViewModel = ViewModelProvider(this, factory)
            .get(MeasuringMenuViewModel::class.java)

        measuringMenuViewModel.startMeasure(applicationContext)

        setContent {
            MainMenu {
                measuringMenuViewModel.endActivity().observe(this){
                    if(it!=null)
                        Log.d("MyTag", "Activity Ended: " + it)
                        startActivity(Intent(this, StartMenuActivity::class.java)).apply {
                            finish()
                        }
                }
            }
        }
    }
}

@Composable
fun MainMenu(stopMeasuring: () -> Unit){
    WearAppTheme{
        val listState = rememberScalingLazyListState()
        val contentModifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        val iconModifier = Modifier.size(24.dp).wrapContentSize(align = Alignment.Center)

        Scaffold(
            timeText = { TimeText(timeTextStyle = TextStyle(fontSize = 15.sp)) },
            vignette = {
                Vignette(vignettePosition = VignettePosition.TopAndBottom)
            },
            positionIndicator = {
                PositionIndicator(
                    scalingLazyListState = listState
                )
            }
        ) {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                anchorType = ScalingLazyListAnchorType.ItemStart,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                flingBehavior = ScalingLazyColumnDefaults.snapFlingBehavior(state = listState),
                state = listState
            ) {
                item { registerStatusChip(contentModifier, iconModifier) }
                item { stopActivityButton(contentModifier, iconModifier, stopMeasuring) }
            }
        }
    }
}

@Composable
fun stopActivityButton(modifier : Modifier = Modifier, iconModifier: Modifier = Modifier, stopMeasuring: () -> Unit){
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Button(
            modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
            onClick = { stopMeasuring() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
        ) {
            Icon(
                imageVector = Icons.Rounded.StopCircle,
                contentDescription = "Para la medición para la actividad",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            modifier = modifier,
            textAlign = TextAlign.Center,
            text = "PARAR MEDICIÓN"
        )
    }

}

@Composable
fun registerStatusChip(modifier: Modifier = Modifier, iconModifier: Modifier = Modifier){
    Chip(
        modifier = modifier,
        onClick = { /**/ },
        label = {
            Text(
                text = "Registrar estado",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.AddCircle,
                contentDescription = "Icono de registrar estado",
                modifier = iconModifier
            )
        },
    )
}