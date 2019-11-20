package com.apptt.axdecor.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Paint(
    val idPaint: Int,
    val name: String,
    val vendorCode: String,
    val rgbCode: String,
    val hexCode: String,
    val presentacion: String,
    val price: String,
    val idProvider: Int,
    val provider: String?
) : Parcelable