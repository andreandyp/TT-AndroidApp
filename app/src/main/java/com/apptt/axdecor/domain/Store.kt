package com.apptt.axdecor.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Store(
    val id_store: Int,
    val address: String,
    val phone: String,
    val email: String,
    val id_provider: Int,
    val logo:String
) : Parcelable