package com.apptt.axdecor.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPaint(
    val idPaint: Int,
    val name: String,
    val vendorCode: String,
    val rgbCode: String,
    val hexCode: String,
    val presentacion: String,
    val price: String,
    val Provider_idProvider: Int
)