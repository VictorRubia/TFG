package com.victorrubia.tfg.presentation.status_menu

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.victorrubia.tfg.R
import com.victorrubia.tfg.presentation.emotions_menu.EmotionsMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme

class StatusMenuActivity :  ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent{
            StatusList(){
                startActivity(EmotionsMenuActivity.intent(this,it)).apply { finish() }
            }
        }
    }
}


@Composable
fun StatusList(selectedItem: (ArrayList<String>) -> Unit){
    val selectedTiles = remember { List(16){ mutableStateOf(false)} }
    val selectedTilesNames = remember { ArrayList<String>() }
    WearAppTheme {
        val listState = rememberScalingLazyListState()
        Scaffold(
            timeText = {
                if (!listState.isScrollInProgress) {
                    TimeText()
                }
            },
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
                state = listState
            ) {
                item { statusCards("Esperando", R.drawable.status_esperando, selectedTiles[0], selectedTilesNames) }
                item { statusCards("En viaje", R.drawable.en_viaje, selectedTiles[1], selectedTilesNames) }
                item { statusCards("Trasbordo", R.drawable.status_trasbordo, selectedTiles[2], selectedTilesNames) }
                item { statusCards("En destino", R.drawable.status_en_destino, selectedTiles[3], selectedTilesNames) }
                item { statusCards("Larga espera", R.drawable.status_larga_espera, selectedTiles[4], selectedTilesNames) }
                item { statusCards("Llego tarde", R.drawable.status_llego_tarde, selectedTiles[5], selectedTilesNames) }
                item { statusCards("Sin información", R.drawable.status_sin_informacion, selectedTiles[6], selectedTilesNames) }
                item { statusCards("Abarrotado", R.drawable.status_abarrotado, selectedTiles[7], selectedTilesNames) }
                item { statusCards("Sentado", R.drawable.status_sentado, selectedTiles[8], selectedTilesNames) }
                item { statusCards("Ruido", R.drawable.status_ruido, selectedTiles[9], selectedTilesNames) }
                item { statusCards("Calor", R.drawable.status_calor, selectedTiles[10], selectedTilesNames) }
                item { statusCards("Frio", R.drawable.status_frio, selectedTiles[11], selectedTilesNames) }
                item { statusCards("Atasco", R.drawable.status_atasco, selectedTiles[12], selectedTilesNames) }
                item { statusCards("Acompañado", R.drawable.status_acompaniado, selectedTiles[13], selectedTilesNames) }
                item { statusCards("Solo", R.drawable.status_solo, selectedTiles[14], selectedTilesNames) }
                item { statusCards("Peligro", R.drawable.status_peligro, selectedTiles[15], selectedTilesNames) }
                item { Spacer(Modifier.height(13.dp)) }
                item { finishedRegisteringStatusChip(selectedItem, selectedTilesNames) }
            }
        }
    }
}

@Composable
fun statusCards(text : String, icon : Int, isSelected : MutableState<Boolean>, selectedTileNames : ArrayList<String>){
    AppCard(
        appImage = {
            if(isSelected.value)
                Icon(
                    imageVector = Icons.Rounded.CheckBox,
                    tint = Color.Green,
                    contentDescription = "Seleccionado",
                )
            else
                Icon(
                    imageVector = Icons.Rounded.CheckBoxOutlineBlank,
                    contentDescription = "No seleccionado",
                )
        },
        appName = {},
        time = { },
        title = { },
        onClick = { 
            isSelected.value = !isSelected.value
            if(isSelected.value){
                selectedTileNames.add(text)
            }
            else{
                selectedTileNames.remove(text)
            }
                  },
        backgroundPainter = if (isSelected.value) ColorPainter(Color.DarkGray) else CardDefaults.cardBackgroundPainter(),
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = text,
                    modifier = Modifier.size(width = 100.dp, height = 100.dp)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = text.uppercase()
                )
            }
        },
    )
}

@Composable
fun finishedRegisteringStatusChip(selectedItem: (ArrayList<String>) -> Unit, selectedTileNames: ArrayList<String>){
    Chip(
        onClick = { selectedItem(selectedTileNames) },
        label = {
            Text(
                text = "Registrar",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "Icono de terminar registro",
            )
        },
    )
}