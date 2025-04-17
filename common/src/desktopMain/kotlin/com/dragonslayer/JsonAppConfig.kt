package com.dragonslayer

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*
import java.io.File

class JsonAppConfig(private val json: JsonObject) : AppConfig {

    override fun getString(key: String): String? =
        json[key]?.jsonPrimitive?.contentOrNull

    override fun getInt(key: String): Int? =
        json[key]?.jsonPrimitive?.intOrNull

    override fun getBoolean(key: String): Boolean? =
        json[key]?.jsonPrimitive?.booleanOrNull

    override fun getDouble(key: String): Double? =
        json[key]?.jsonPrimitive?.doubleOrNull

    override fun <T> getValue(key: String, deserializer: DeserializationStrategy<T>): T? {
        val element = json[key] ?: return null
        return try {
            Json.decodeFromJsonElement(deserializer, element)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        fun fromFile(file: File): JsonAppConfig {
            val text = file.readText()
            val jsonObject = Json.decodeFromString<JsonObject>(text)
            return JsonAppConfig(jsonObject)
        }
        fun fromString(text: String): JsonAppConfig {
            val jsonObject = Json.decodeFromString<JsonObject>(text)
            return JsonAppConfig(jsonObject)
        }
    }
}