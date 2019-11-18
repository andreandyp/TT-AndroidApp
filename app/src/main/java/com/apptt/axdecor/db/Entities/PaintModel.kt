package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class PaintModel(
    @ColumnInfo(name = "id_paint") @PrimaryKey @NonNull val idPaint: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "vendor_code") val vendorCode: String,
    @ColumnInfo(name = "rgb_code") val rgbCode: String,
    @ColumnInfo(name = "hex_code") val hexCode: String,
    @ColumnInfo(name = "presentacion") val presentacion: String,
    @ColumnInfo(name = "price") val price: String,
    @ForeignKey(
        entity = ProviderModel::class,
        parentColumns = ["id_provider"],
        childColumns = ["id_provider"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
    val idProvider: Int
)