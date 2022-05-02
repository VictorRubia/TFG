package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.user.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user : User)

    @Query("DELETE FROM current_user")
    suspend fun deleteUser()

    @Query("SELECT * FROM current_user")
    suspend fun getUser() : User

}