package com.victorrubia.tfg.data.model.ppg_measure


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ppg_measures")
data class PPGMeasure(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("activity_id")
    val activityId: Int,
    @SerializedName("measurement")
    val measurement: String,
    @SerializedName("url")
    val url: String
)