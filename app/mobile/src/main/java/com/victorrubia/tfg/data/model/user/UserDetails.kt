package com.victorrubia.tfg.data.model.user

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
)