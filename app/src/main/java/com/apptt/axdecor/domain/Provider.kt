package com.apptt.axdecor.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Provider(
    val idProvider: Int,
    val name: String,
    val rfc: String,
    val razonSocial: String,
    val persona: String,
    val rango: String
) : Parcelable