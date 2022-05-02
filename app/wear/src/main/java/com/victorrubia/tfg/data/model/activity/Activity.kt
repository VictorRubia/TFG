package com.victorrubia.tfg.data.model.activity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "current_activity")
data class Activity(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("start_d")
    val startD: String,
    @SerializedName("end_d")
    val endD: String?,
)