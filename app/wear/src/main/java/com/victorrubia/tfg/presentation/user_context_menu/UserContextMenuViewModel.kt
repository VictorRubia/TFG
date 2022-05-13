package com.victorrubia.tfg.presentation.user_context_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

class UserContextMenuViewModel : ViewModel()  {

    fun delayAnnouncement() = liveData<Boolean> {
        delay(4000)
        emit(true)
    }

}