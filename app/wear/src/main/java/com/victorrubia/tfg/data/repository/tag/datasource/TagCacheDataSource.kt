package com.victorrubia.tfg.data.repository.tag.datasource

import com.victorrubia.tfg.data.model.tag.Tag

interface TagCacheDataSource {
    suspend fun getTagsFromCache() : List<Tag>
    suspend fun saveTagToCache(tag : Tag)
    suspend fun clearAll()
}