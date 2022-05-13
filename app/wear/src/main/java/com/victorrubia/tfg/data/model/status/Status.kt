package com.victorrubia.tfg.data.model.status

@kotlinx.serialization.Serializable
data class Status(
    val status: String,
    val context: ArrayList<String>,
    val emotions: String,
    val feelings: String,
)