package com.victorrubia.tfg.data.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.ZonedDateTime
import java.util.*

class Converters {

    @TypeConverter
    fun fromDate(date: Date): String{
        return Json.encodeToString(date)
    }

    @TypeConverter
    fun stringToDate(jsonDate: String): Date{
        return Json.decodeFromString(jsonDate)
    }

}