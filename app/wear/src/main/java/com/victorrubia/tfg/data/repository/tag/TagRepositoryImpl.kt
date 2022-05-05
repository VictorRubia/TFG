package com.victorrubia.tfg.data.repository.tag

import android.util.Log
import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.data.repository.tag.datasource.TagCacheDataSource
import com.victorrubia.tfg.data.repository.tag.datasource.TagLocalDataSource
import com.victorrubia.tfg.data.repository.tag.datasource.TagRemoteDataSource
import com.victorrubia.tfg.domain.repository.TagRepository
import java.util.*


class TagRepositoryImpl(
    private val tagRemoteDataSource : TagRemoteDataSource,
    private val tagLocalDataSource: TagLocalDataSource,
    private val tagCacheDataSource: TagCacheDataSource
) : TagRepository {

    override suspend fun addTag(tag: String, datetime: Date, activityId: Int): Tag {
        return addTagToCache(tag, datetime, activityId)
    }

    suspend fun addTagToAPI(tag: String, datetime: Date, activityId: Int) : Tag{
        try {
            val response = tagRemoteDataSource.addTag(tag, datetime, activityId)
            if(response.body() != null){
                tagLocalDataSource.clearAll()
                tagCacheDataSource.clearAll()
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return Tag(tag, datetime, activityId)
    }

    suspend fun addTagToDB(tag: String, datetime: Date, activityId: Int) : Tag{
        try {
            tagLocalDataSource.saveTagToDB(Tag(tag, datetime, activityId))
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return addTagToAPI(tag, datetime, activityId)
    }

    suspend fun addTagToCache(tag: String, datetime: Date, activityId: Int) : Tag{
        try {
            tagCacheDataSource.saveTagToCache(Tag(tag, datetime, activityId))
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return addTagToDB(tag, datetime, activityId)

    }
}