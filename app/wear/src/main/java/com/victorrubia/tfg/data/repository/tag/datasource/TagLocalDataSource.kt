package com.victorrubia.tfg.data.repository.tag.datasource

import com.victorrubia.tfg.data.model.tag.Tag

interface TagLocalDataSource {
    suspend fun getTagsFromDB() : List<Tag>
    suspend fun saveTagToDB(tag : Tag)
    suspend fun clearAll()
}