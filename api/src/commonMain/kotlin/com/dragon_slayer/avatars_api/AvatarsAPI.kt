package com.dragon_slayer.avatars_api

interface AvatarsAPI {
    /**
     * the method returns a list of links to images
     */
    suspend fun getAvatars(): List<String>
}