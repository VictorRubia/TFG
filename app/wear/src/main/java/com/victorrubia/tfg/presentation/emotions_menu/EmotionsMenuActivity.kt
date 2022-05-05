package com.victorrubia.tfg.presentation.emotions_menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.victorrubia.tfg.presentation.feelings_menu.FeelingsMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme

class EmotionsMenuActivity: ComponentActivity() {

    companion object{
        private const val tiles = "tilesSelected"
        fun intent(context: Context, tilesSelected: ArrayList<String>)=
            Intent(context, EmotionsMenuActivity::class.java).apply {
                putStringArrayListExtra(tiles,tilesSelected)
            }
    }

    private val tilesSelected : ArrayList<String> by lazy {
        intent?.getSerializableExtra(tiles) as ArrayList<String>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            EmotionsList(tilesSelected){
                startActivity(FeelingsMenuActivity.intent(this,it))
                finish()
            }
        }
    }
}


@Composable
fun EmotionsList(tilesSelected: ArrayList<String>, selectedItem: (ArrayList<String>) -> Unit){
    val selectedTiles = remember { List(6){ mutableStateOf(false) } }
    val selectedTilesNames = remember { tilesSelected }
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
                item { emotionsCards("Felicidad", "😀", selectedTiles[0], selectedTilesNames) }
                item { emotionsCards("Tristeza", "😢", selectedTiles[1], selectedTilesNames) }
                item { emotionsCards("Miedo", "😱", selectedTiles[2], selectedTilesNames) }
                item { emotionsCards("Ira", "😠", selectedTiles[3], selectedTilesNames) }
                item { emotionsCards("Sorpresa", "😯", selectedTiles[4], selectedTilesNames) }
                item { emotionsCards("Asco", "😒", selectedTiles[5], selectedTilesNames) }
                item { Spacer(Modifier.height(13.dp)) }
                item { finishedRegisteringEmotionsChip(selectedItem, selectedTilesNames) }
            }
        }
    }
}

@Composable
fun emotionsCards(text : String, icon : String, isSelected : MutableState<Boolean>, selectedTileNames : ArrayList<String>){
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
                Text(
                    textAlign = TextAlign.Center,
                    text = icon.uppercase(),
                    fontSize = 60.sp,
                    modifier = Modifier.fillMaxWidth().height(75.dp)
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
fun finishedRegisteringEmotionsChip(selectedItem: (ArrayList<String>) -> Unit, selectedTileNames: ArrayList<String>){
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