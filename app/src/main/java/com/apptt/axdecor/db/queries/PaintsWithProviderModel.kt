package com.apptt.axdecor.db.queries

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PaintsWithProviderModel(
    @ColumnInfo(name = "id_paint") @PrimaryKey @NonNull val idPaint: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "vendor_code") val vendorCode: String,
    @ColumnInfo(name = "rgb_code") val rgbCode: String,
    @ColumnInfo(name = "hex_code") val hexCode: String,
    @ColumnInfo(name = "presentacion") val presentacion: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "idProvider") val idProvider: Int,
    @ColumnInfo(name = "proveedor") val proveedor: String
)