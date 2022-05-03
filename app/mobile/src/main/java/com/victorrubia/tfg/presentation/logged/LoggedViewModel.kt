package com.victorrubia.tfg.presentation.logged

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.IsWearConnectedUseCase
import com.victorrubia.tfg.domain.usecase.RemoveLocalUserUseCase
import com.victorrubia.tfg.domain.usecase.SendApiKeyToWearUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoggedViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val removeLocalUserUseCase: RemoveLocalUserUseCase,
    private val sendApiKeyToWearUseCase: SendApiKeyToWearUseCase,
    private val isWearConnectedUseCase: IsWearConnectedUseCase,
) : ViewModel(){

    fun sendApiKeyToWear(){
        CoroutineScope(Dispatchers.IO).launch {
            sendApiKeyToWearUseCase.execute()
        }
    }

    fun isWearConnected() = liveData {
        emit(isWearConnectedUseCase.execute())
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