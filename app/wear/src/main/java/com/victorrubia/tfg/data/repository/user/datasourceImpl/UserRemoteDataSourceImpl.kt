package com.victorrubia.tfg.data.repository.user.datasourceImpl

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRemoteDataSourceImpl(
    context : Context
) : UserRemoteDataSource {

    private var bestNodeID: String? = null
    private var _nodesConnected : MutableLiveData<Boolean?> = MutableLiveData<Boolean?>(false)
    private var context = context


    override suspend fun requestUser() {
        updateBestNode(context)
        initMessageBroadcaster(context)
        broadcastMessage(context)
    }

    private fun updateBestNode(context: Context) {
        try {
            val info = Tasks.await(
                Wearable.getCapabilityClient(context)
                    .getCapability("api_key_sender", CapabilityClient.FILTER_REACHABLE)
            )
            for (node in info.nodes) {
                if (node.isNearby) {
                    bestNodeID = node.id
                    _nodesConnected.postValue(true)
                }
            }
        } catch (e: Exception) {
            // Don't call printStackTrace() because that would make an infinite loop
            Log.e("apde", e.toString())
        }
    }

    private fun initMessageBroadcaster(context: Context?) {

        if (context != null) {
            Wearable.getCapabilityClient(context).addListener({ updateBestNode(context) }, "apde_receive_logs")
        }

        // Can't do this on the main thread
        CoroutineScope(Dispatchers.IO).launch {
            updateBestNode(context!!)
        }

    }

    private fun broadcastMessage(context: Context?) {
        Handler(Looper.getMainLooper()).post {
            try {
                if (context != null) {
                    bestNodeID?.let { Wearable.getMessageClient(context).sendMessage(it, "api_key_sender", ByteArray(0)) }
                }
            } catch (e: java.lang.Exception) {
                // Don't call printStackTrace() because that would make an infinite loop
                Log.e("apde", e.toString())
            }
        }
    }

}