package com.victorrubia.tfg.presentation.logged

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.RemoveLocalUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoggedViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val removeLocalUserUseCase: RemoveLocalUserUseCase
) : ViewModel(){

    private var bestNodeID: String? = null
    private var _nodesConnected : MutableLiveData<Boolean?> = MutableLiveData<Boolean?>(false)
    val nodesConnected : LiveData<Boolean?>
        get() = _nodesConnected

    private fun updateBestNode(context: Context) {
        try {
            val info = Tasks.await(
                Wearable.getCapabilityClient(context)
                    .getCapability("api_key", CapabilityClient.FILTER_REACHABLE)
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


    fun initMessageBroadcaster(context: Context?) {

        Wearable.getCapabilityClient(context).addListener({ updateBestNode(context!!) }, "apde_receive_logs")

        // Can't do this on the main thread
        CoroutineScope(Dispatchers.IO).launch {
            updateBestNode(context!!)
        }

    }

    fun broadcastMessage(message: String, context: Context?) {
        Handler(Looper.getMainLooper()).post {
            try {
                if (bestNodeID != null) {
                    Wearable.getMessageClient(context).sendMessage(bestNodeID, "api_key", message.toByteArray())
                }
            } catch (e: java.lang.Exception) {
                // Don't call printStackTrace() because that would make an infinite loop
                Log.e("apde", e.toString())
            }
        }
    }

    fun getUser(email : String = "", password : String = "") = liveData {
        val user = getUserUseCase.execute(email, password)
        emit(user)
    }

    fun removeLocalUser() {
        viewModelScope.launch{
            removeLocalUserUseCase.execute()
        }
    }

}