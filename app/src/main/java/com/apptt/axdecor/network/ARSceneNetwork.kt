package com.apptt.axdecor.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkARScene(
    val idARScene: Int,
    val name: String,
    val imagen: String
)