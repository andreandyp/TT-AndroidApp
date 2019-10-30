package com.apptt.axdecor.Network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkModel(
    val idModel: Int,
    val name: String,
    val fileAR: String,
    val price: String,
    val color: String,
    val description: String,
    val file2D: String,
    val createdAt: String,
    val updatedAt: String,
    val Provider_idProvider: Int,
    val Type_idType: Int,
    @Json(name = "predefinedstyles")
    val styles: List<NetworkStyle>,
    val categories: List<NetworkCategory>
)

@JsonClass(generateAdapter = true)
data class NetworkStyle(
    val idPredefinedStyle: Int
)