package com.dragon_slayer.firebase.data

import io.ktor.client.statement.*

const val NO_DATA = "none"

suspend fun HttpResponse.isNotNull(): Boolean = this.bodyAsText() != "null"