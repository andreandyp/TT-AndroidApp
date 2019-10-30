package com.apptt.axdecor.Network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkDataContainer(
    val tipos: List<NetworkFullType>,
    val estilos: List<NetworkFullStyle>,
    val categorias: List<NetworkFullCategory>
)

@JsonClass(generateAdapter = true)
data class NetworkFullType(
    val idType: Int,
    val nameType: String

)

@JsonClass(generateAdapter = true)
data class NetworkFullStyle(
    val idPredefinedStyle: Int,
    val style: String

)

@JsonClass(generateAdapter = true)
data class NetworkFullCategory(
    val idCategory: Int,
    val category: String

)
