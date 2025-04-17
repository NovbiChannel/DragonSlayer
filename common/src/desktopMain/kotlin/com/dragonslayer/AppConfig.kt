package com.dragonslayer

import kotlinx.serialization.DeserializationStrategy

interface AppConfig {
    fun getString(key: String): String?
    fun getInt(key: String): Int?
    fun getBoolean(key: String): Boolean?
    fun getDouble(key: String): Double?
    fun <T> getValue(key: String, deserializer: DeserializationStrategy<T>): T?
}