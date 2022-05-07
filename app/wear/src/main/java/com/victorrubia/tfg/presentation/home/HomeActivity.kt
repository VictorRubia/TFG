package com.victorrubia.tfg.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material.icons.rounded.CloudSync
import com.victorrubia.tfg.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.wear.ambient.AmbientModeSupport
import androidx.wear.compose.material.*
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.start_menu.StartMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme
import java.util.*
import javax.inject.Inject

class HomeActivity : ComponentActivity(), MessageClient.OnMessageReceivedListener {

    @Inject
    lateinit var factory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        (application as Injector).createHomeSubComponent()
            .inject(this)
        homeViewModel = ViewModelProvider(this, factory)
            .get(HomeViewModel::class.java)

        Wearable.getMessageClient(this).addListener(this)

        homeViewModel.requestUser()

        setContent {
            HomeComponent()
        }
    }

//    fun createActivity(){
//        val responseLiveData = homeViewModel.newActivity("Prueba", Calendar.getInstance().time.toString())
//        responseLiveData.observe(this, Observer { if(it != null){ } })
//    }

    override fun onMessageReceived(p0: MessageEvent) {
        Log.i("MyTag", "Mensaje recibido: " + String(p0.data))
        homeViewModel.saveUser(User(String(p0.data)))
        startActivity(Intent(this, StartMenuActivity::class.java))
        finish()
    }

}

@Composable
fun HomeComponent(){
    WearAppTheme {
        Scaffold(
            timeText = {
                TimeText(
                    timeTextStyle = TextStyle(fontSize = 15.sp)
                )
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(width = 100.dp,height = 100.dp),
                    imageVector = Icons.Rounded.CloudSync,
                    contentDescription = "Icono sincronización de datos",
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "SINCRONIZANDO"
                )
            }
        }
    }
}