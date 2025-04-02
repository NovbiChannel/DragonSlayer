package com.dragon_slayer.avatars_api

import engineFactory
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class AvatarsApiImpl: AvatarsAPI {
    private val client = HttpClient(engineFactory())
    override suspend fun getAvatars(): List<String> {
        val pageContent = getPageContent()
        return extractImageUrls(pageContent)
    }

    private suspend fun getPageContent(): String {
        return client.get("https://randomavatar.com/").bodyAsText()
    }

    private fun extractImageUrls(html: String): List<String> {
        val regex = """src="(https://cdn\.cloudflare\.steamstatic\.com[^"]+)"""".toRegex()
        val matches = regex.findAll(html)
        return matches.map { it.groupValues[1] }.toList()
    }
}