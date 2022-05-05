package com.victorrubia.tfg.presentation.activity_type

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.DirectionsRailway
import androidx.compose.material.icons.rounded.Subway
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.victorrubia.tfg.presentation.activity_confirmation.ActivityConfirmationActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme

class ActivityTypeActivity :  ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            ActivityTypeList{
                startActivity(ActivityConfirmationActivity.intent(this,it))
                finish()
            }
        }
    }
}

@Composable
fun ActivityTypeList(createActivity: (String) -> Unit){
    WearAppTheme {
        val listState = rememberScalingLazyListState()
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
                item{ activityTypeChip("Viajar en bus", Icons.Rounded.DirectionsBus, createActivity)}
                item{ activityTypeChip("Viajar en metro", Icons.Rounded.Subway, createActivity)}
                item{ activityTypeChip("Viajar en tren", Icons.Rounded.DirectionsRailway, createActivity)}
            }
        }
    }
}

@Composable
fun activityTypeChip(text : String, icon : ImageVector, createActivity: (String) -> Unit){
    var loading by remember { mutableStateOf(true) }
    Chip(
        onClick = { createActivity(text)
                  loading = false},
        enabled = loading,
        label = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icono de la actividad",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}