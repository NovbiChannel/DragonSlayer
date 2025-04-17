package ru.chaglovne.l2.database.data

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseConfig(
    val name: String,
    val url: String,
    val driver: String
)