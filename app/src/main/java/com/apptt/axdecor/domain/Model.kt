package com.apptt.axdecor.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Model(
    val idModel: Int,
    val name: String,
    val fileAR: String,
    val price: String,
    val description: String,
    val file2D: String,
    val color: String,
    val medidas: String?,
    val codigo: String?,
    val styles: List<String>
) : Parcelable