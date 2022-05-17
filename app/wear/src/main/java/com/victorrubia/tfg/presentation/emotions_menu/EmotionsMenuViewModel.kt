package com.victorrubia.tfg.presentation.emotions_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

class EmotionsMenuViewModel : ViewModel()  {

    fun delayAnnouncement() = liveData<Boolean> {
        delay(2500)
        emit(true)
    }

}