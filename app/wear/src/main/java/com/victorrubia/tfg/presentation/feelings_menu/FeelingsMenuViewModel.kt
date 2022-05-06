package com.victorrubia.tfg.presentation.feelings_menu

import androidx.lifecycle.ViewModel
import com.victorrubia.tfg.data.model.status.Status
import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.domain.usecase.AddTagUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.collections.ArrayList

class FeelingsMenuViewModel(
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
    private val addTagUseCase: AddTagUseCase,
) : ViewModel() {

    fun addTag( statusTags : ArrayList<String>, emotionsTags : ArrayList<String>, feelingsTags : ArrayList<String>){
        CoroutineScope(Dispatchers.IO).launch {
            getCurrentActivityUseCase.execute()?.let { addTagUseCase.execute(Json.encodeToString(Status(statusTags, emotionsTags, feelingsTags)), Date(), it.id) }
        }
    }
}