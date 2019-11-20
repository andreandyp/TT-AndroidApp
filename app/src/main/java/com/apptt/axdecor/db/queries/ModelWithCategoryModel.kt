package com.apptt.axdecor.db.queries

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelWithCategoryModel(
    @ColumnInfo(name = "id_model") @PrimaryKey @NonNull val idModel: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "fileAR") val fileAR: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "file2D") val file2D: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "medidas") val medidas: String?,
    @ColumnInfo(name = "codigo") val codigo: String?,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "proveedor") val proveedor: String,
    @ColumnInfo(name = "id_provider") val idProvider: Int
)