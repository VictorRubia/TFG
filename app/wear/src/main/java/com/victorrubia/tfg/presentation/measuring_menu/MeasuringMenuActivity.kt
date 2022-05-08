package com.victorrubia.tfg.presentation.measuring_menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material.icons.rounded.StopCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.material.*
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.start_menu.StartMenuActivity
import com.victorrubia.tfg.presentation.status_menu.StatusMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme
import javax.inject.Inject

class MeasuringMenuActivity: ComponentActivity() {

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
            MainMenu( remember { measuringMenuViewModel.internetStatus } ,{
                measuringMenuViewModel.endActivity().observe(this){
                    if(it!=null){
                        Log.d("MyTag", "Activity Ended: $it")
                        startActivity(Intent(this, StartMenuActivity::class.java))
                        finish()
                    }
                }
            },
                {
                    startActivity(Intent(this, StatusMenuActivity::class.java))
                }
            )
        }
    }
}

@Composable
fun MainMenu(internetStatus: MutableState<Boolean>, stopMeasuring: () -> Unit, registerStatus: () -> Unit){
    var loading = remember { mutableStateOf(true) }
    WearAppTheme{
        val listState = rememberScalingLazyListState()
        val contentModifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 8.dp)
        val iconModifier = Modifier.size(24.dp).wrapContentSize(align = Alignment.Center)

        Scaffold(
            timeText = { if(internetStatus.value) TimeText(timeTextStyle = TextStyle(fontSize = 15.sp)) },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(!internetStatus.value){
                    noInternet()
                }
                else{
                    Spacer(Modifier.height(45.dp))
                }
                registerStatusChip(contentModifier, iconModifier, registerStatus)
                stopActivityButton(contentModifier, internetStatus, stopMeasuring, loading)
            }
        }
    }
}

@Composable
fun stopActivityButton(modifier : Modifier = Modifier, internetStatus: MutableState<Boolean>, stopMeasuring: () -> Unit, isStopped : MutableState<Boolean>){
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Button(
            modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
            onClick = { stopMeasuring()
                      isStopped.value = false
                      },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            enabled = isStopped.value and internetStatus.value
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
fun registerStatusChip(modifier: Modifier = Modifier, iconModifier: Modifier = Modifier, registerStatus: () -> Unit){
    Chip(
        modifier = modifier,
        onClick = { registerStatus() },
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

@Composable
fun noInternet(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(width = 25.dp, height = 25.dp),
            imageVector = Icons.Rounded.CloudOff,
            contentDescription = "Icono no internet",
        )
        Text(
            textAlign = TextAlign.Center,
            text = "SIN CONEXIÓN",
            fontSize = 13.sp,
        )
    }
}