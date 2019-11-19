package com.apptt.axdecor.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkARScene(
    val idARScene: Int,
    val name: String,
    val imagen: String,
    val Provider_idProvider: Int,
    val type: NetworkType,
    @Json(name = "predefinedstyle")
    val style: NetworkStyle,
    val models: List<NetworkModelScene>
)

@JsonClass(generateAdapter = true)
data class NetworkModelScene(
    val idModel: Int
)
