package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.activity.Activity

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveActivity(activity: Activity)

    @Query("DELETE FROM current_activity")
    suspend fun deleteActivity()

    @Query("SELECT * FROM current_activity")
    suspend fun getActivity() : Activity

}