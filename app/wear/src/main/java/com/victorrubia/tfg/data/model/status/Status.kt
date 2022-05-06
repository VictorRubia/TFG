package com.victorrubia.tfg.data.model.status

@kotlinx.serialization.Serializable
data class Status(
    val status: ArrayList<String>,
    val emotions: ArrayList<String>,
    val feelings: ArrayList<String>
)