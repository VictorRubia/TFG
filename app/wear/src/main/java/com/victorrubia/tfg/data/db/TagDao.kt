package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.tag.Tag

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTag(tag: Tag)

    @Query("DELETE FROM current_tags")
    suspend fun deleteTags()

    @Query("SELECT * FROM current_tags")
    suspend fun getTags() : List<Tag>

}