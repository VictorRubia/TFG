package com.victorrubia.tfg.presentation.feelings_menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.material.*
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.emotions_menu.EmotionsMenuActivity
import com.victorrubia.tfg.presentation.emotions_menu.emotionsCards
import com.victorrubia.tfg.presentation.emotions_menu.finishedRegisteringEmotionsChip
import com.victorrubia.tfg.ui.theme.WearAppTheme
import javax.inject.Inject

class FeelingsMenuActivity: ComponentActivity() {

    companion object{
        private const val STATUS_TILES = "statusTilesSelected"
        private const val EMOTION_TILES = "emotionTilesSelected"
        fun intent(context: Context, statusTiles: ArrayList<String>, emotionTiles: ArrayList<String>)=
            Intent(context, FeelingsMenuActivity::class.java).apply {
                putStringArrayListExtra(STATUS_TILES,statusTiles)
                putStringArrayListExtra(EMOTION_TILES,emotionTiles)
            }
    }

    private val statusTilesSelected : ArrayList<String> by lazy {
        intent?.getSerializableExtra(STATUS_TILES) as ArrayList<String>
    }

    private val emotionTilesSelected : ArrayList<String> by lazy {
        intent?.getSerializableExtra(EMOTION_TILES) as ArrayList<String>
    }

    @Inject
    lateinit var factory: FeelingsMenuViewModelFactory
    private lateinit var feelingsMenuViewModel: FeelingsMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        (application as Injector).createFeelingsMenuSubComponent()
            .inject(this)
        feelingsMenuViewModel = ViewModelProvider(this, factory)
            .get(FeelingsMenuViewModel::class.java)

        setContent{
            FeelingsList(ArrayList<String>()){
                feelingsMenuViewModel.addTag(statusTilesSelected, emotionTilesSelected, it)
                finish()
            }
        }
    }
}

@Composable
fun FeelingsList(tilesSelected: ArrayList<String>, selectedItem: (ArrayList<String>) -> Unit){
    val selectedTiles = remember { List(2){ mutableStateOf(false) } }
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
                item { emotionsCards("Nervioso", "😬", selectedTiles[0], selectedTilesNames) }
                item { emotionsCards("Tranquilo", "😇", selectedTiles[1], selectedTilesNames) }
                item { Spacer(Modifier.height(13.dp)) }
                item { finishedRegisteringEmotionsChip(selectedItem, selectedTilesNames) }
            }
        }
    }
}