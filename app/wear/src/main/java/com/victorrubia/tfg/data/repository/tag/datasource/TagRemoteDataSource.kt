package com.victorrubia.tfg.data.repository.tag.datasource

import com.victorrubia.tfg.data.model.tag.Tag
import retrofit2.Response
import java.util.*

interface TagRemoteDataSource {

    suspend fun addTag(tag : String, datetime : Date, activityId : Int) : Response<Tag>
}