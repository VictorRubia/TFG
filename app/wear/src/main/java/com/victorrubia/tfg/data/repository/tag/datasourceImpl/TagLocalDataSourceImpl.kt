package com.victorrubia.tfg.data.repository.tag.datasourceImpl

import com.victorrubia.tfg.data.db.TagDao
import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.data.repository.tag.datasource.TagLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagLocalDataSourceImpl(
    private val tagDao: TagDao
) : TagLocalDataSource {

    override suspend fun getTagsFromDB(): List<Tag> {
        return tagDao.getTags()
    }

    override suspend fun saveTagToDB(tag: Tag) {
        CoroutineScope(Dispatchers.IO).launch {
            tagDao.saveTag(tag)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            tagDao.deleteTags()
        }
    }
}