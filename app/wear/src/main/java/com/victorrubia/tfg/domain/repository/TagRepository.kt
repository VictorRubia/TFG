package com.victorrubia.tfg.domain.repository

import com.victorrubia.tfg.data.model.tag.Tag
import java.util.Date

interface TagRepository {

    suspend fun addTag(tag : String, datetime : Date, activityId : Int) : Tag
}