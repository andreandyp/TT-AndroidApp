package com.apptt.axdecor.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkModel(
    val idModel: Int,
    val name: String,
    val fileAR: String?,
    val price: String,
    val color: String,
    val description: String,
    val file2D: String,
    val createdAt: String,
    val updatedAt: String,
    val Provider_idProvider: Int,
    @Json(name = "predefinedstyles")
    val styles: List<NetworkStyle>,
    val categories: List<NetworkCategory>,
    val types: List<NetworkType>,
    val medidas: String?,
    val codigo: String?
)

@JsonClass(generateAdapter = true)
data class NetworkStyle(
    val idPredefinedStyle: Int
)

@JsonClass(generateAdapter = true)
data class NetworkType(
    val idType: Int
)