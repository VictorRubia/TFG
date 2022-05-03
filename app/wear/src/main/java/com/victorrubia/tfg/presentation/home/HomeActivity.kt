package com.victorrubia.tfg.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import com.victorrubia.tfg.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.material.*
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.ui.theme.WearAppTheme
import java.util.*
import javax.inject.Inject

class HomeActivity : ComponentActivity(), MessageClient.OnMessageReceivedListener {

    @Inject
    lateinit var factory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        (application as Injector).createHomeSubComponent()
            .inject(this)
        homeViewModel = ViewModelProvider(this, factory)
            .get(HomeViewModel::class.java)

        Wearable.getMessageClient(this).addListener(this)

        var responseLiveData = homeViewModel.requestUser()

        setContent {
            StartMeasure{
                createActivity()
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        Wearable.getMessageClient(this).removeListener(this)
//    }

    fun createActivity(){
        val responseLiveData = homeViewModel.newActivity("Prueba", Calendar.getInstance().time.toString())
        responseLiveData.observe(this, Observer { if(it != null){ } })
    }

    override fun onMessageReceived(p0: MessageEvent) {
        Log.i("MyTag", "Mensaje recibido: " + String(p0.data))
        homeViewModel.saveUser(User(String(p0.data)))
    }

}

@Composable
fun StartMeasure(createActivity: () -> Unit){
    WearAppTheme {
        /* *************************** Part 4: Wear OS Scaffold *************************** */
        // TODO (Start): Create a Scaffold (Wear Version)
        Scaffold(
            timeText = {
                TimeText()
            },
        ) {

            /* *************************** Part 3: ScalingLazyColumn *************************** */
            // TODO: Create a ScalingLazyColumn (Wear's version of LazyColumn)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    modifier = Modifier.size(100.dp,100.dp),
                    onClick = { createActivity() },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_activity),
                        contentDescription = "triggers phone action",
                        modifier = Modifier.size(75.dp,75.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    textAlign = TextAlign.Center,
//                    color = MaterialTheme.colors.primary,
                    text = "COMENZAR\r\nACTIVIDAD"
                )
            }
            // TODO (End): Create a Scaffold (Wear Version)
        }
    }
}