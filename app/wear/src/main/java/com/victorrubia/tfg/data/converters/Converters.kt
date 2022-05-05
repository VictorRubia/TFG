package com.victorrubia.tfg.data.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    @TypeConverter
    fun fromDate(date: Date): String{
        val df = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS")
        return df.format(date)
    }

    @TypeConverter
    fun stringToDate(date: String): Date{
        return SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS").parse(date.toString())

    }

}