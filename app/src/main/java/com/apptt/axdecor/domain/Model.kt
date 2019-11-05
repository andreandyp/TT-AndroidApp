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
    val color: String
    /*
    @ForeignKey(
        entity = ProviderModel::class,
        parentColumns = ["id_provider"],
        childColumns = ["id_provider"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ) val idProvider: Int,
    @ColumnInfo(name = "id_type")
    @ForeignKey(
        entity = TypeModel::class,
        parentColumns = ["id_type"],
        childColumns = ["id_type"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ) val idType: Int*/

) : Parcelable