package com.victorrubia.tfg.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.victorrubia.tfg.data.model.user.UserDetails

class Converters {


    @TypeConverter
    fun fromUserDetails(user: UserDetails?): String? {
        return if(user == null) null else Gson().toJson(user)
    }

    @TypeConverter
    fun stringToUserDetails(jsonUser: String?): UserDetails? {
        val notesType = object : TypeToken<UserDetails>() {}.type
        return Gson().fromJson<UserDetails>(jsonUser, notesType)
    }
}