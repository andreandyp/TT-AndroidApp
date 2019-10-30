package com.apptt.axdecor.Network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkProvider(
    val idProvider: Int,
    val name: String,
    val rfc: String,
    val razonSocial: String,
    val persona: String,
    val rango: String,
    @Json(name = "socialnetworks")
    val socialNetworks: List<NetworkSocialNetwork>,
    val stores: List<NetworkStore>,
    val categories: List<NetworkCategory>
)

@JsonClass(generateAdapter = true)
data class NetworkSocialNetwork(
    val idSocialNetwork: Int,
    val socialNetworkUrl: String
)

@JsonClass(generateAdapter = true)
data class NetworkStore(
    val idStore: Int,
    val address: String,
    val phone: String,
    val email: String
)

@JsonClass(generateAdapter = true)
data class NetworkCategory(
    val idCategory: Int
)
