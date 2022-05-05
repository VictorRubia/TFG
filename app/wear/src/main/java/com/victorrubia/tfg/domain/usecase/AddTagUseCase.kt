package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.domain.repository.TagRepository
import java.util.*

class AddTagUseCase(private val tagRepository: TagRepository) {

    suspend fun execute(tag: String, datetime: Date, activityId: Int) : Tag = tagRepository.addTag(tag, datetime, activityId)

}