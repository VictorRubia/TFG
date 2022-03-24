package com.victorrubia.tfg

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class UserApiListener : WearableListenerService(), MessageClient.OnMessageReceivedListener {
    private val API_KEY_CAPABILITY_NAME = "api_key_sender"

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == API_KEY_CAPABILITY_NAME) {
            Log.d("MESSAGE RECEIVED", String(messageEvent.data))
            Log.d("sender", "Broadcasting message");
            val messageIntent = Intent("API_KEY_sender")
            messageIntent.putExtra("Status", String(messageEvent.data));
            //api_key = String(messageEvent.data)
            LocalBroadcastManager.getInstance(this.applicationContext).sendBroadcast(messageIntent);
        }
    }
}