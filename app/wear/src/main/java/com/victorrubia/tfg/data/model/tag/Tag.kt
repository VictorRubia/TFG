package com.victorrubia.tfg.data.model.tag


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.victorrubia.tfg.data.model.ppg_measure.DateSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Entity(tableName = "current_tags")
@Serializable
data class Tag(
    @SerializedName("tag")
    val tag: String,
    @SerializedName("datetime")
    @PrimaryKey
    @Serializable(with = DateSerializer::class)
    val datetime: Date,
    @SerializedName("activity_id")
    val activityId: Int,
)