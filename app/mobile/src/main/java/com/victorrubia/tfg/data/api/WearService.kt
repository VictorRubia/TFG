package com.victorrubia.tfg.data.api

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.presentation.di.Injector
import javax.inject.Inject

class WearService : WearableListenerService(), MessageClient.OnMessageReceivedListener {

    @Inject
    lateinit var factory: GetUserUseCase

    override fun onCreate() {
        super.onCreate()
        (application as Injector).createWearServiceSubComponent().inject(this)
    }

    private val API_KEY_CAPABILITY_NAME = "api_key_sender"

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == API_KEY_CAPABILITY_NAME) {
            Log.d("Servicio", "Received API Request");
            val receiverID = messageEvent.sourceNodeId

            var responseLiveData = liveData {
                val user = factory.execute("", "")
                emit(user)
            }

            val observer = Observer<User?> {
                if (it != null) {
                    Handler(Looper.getMainLooper()).post {
                        try {
                            Wearable.getMessageClient(applicationContext)
                                .sendMessage(receiverID, "api_key", it.apiKey.toByteArray())
                        } catch (e: java.lang.Exception) {
                            // Don't call printStackTrace() because that would make an infinite loop
                            Log.e("apde", e.toString())
                        }
                    }
                } else {
                    Log.e("Servicio", "ERROR Servicio")
                }
                Log.i("Servicio", it.toString())
            }

            Handler(Looper.getMainLooper()).post { responseLiveData.observeForever(observer) }
        }
    }

}