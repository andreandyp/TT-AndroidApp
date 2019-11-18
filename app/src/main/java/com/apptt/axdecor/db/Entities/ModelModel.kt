package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class ModelModel(
    @ColumnInfo(name = "id_model") @PrimaryKey @NonNull val idModel: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "fileAR") val fileAR: String?,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "file2D") val file2D: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
    @ColumnInfo(name = "medidas") val medidas: String?,
    @ColumnInfo(name = "codigo") val codigo: String?,
    @ColumnInfo(name = "id_provider")
    @ForeignKey(
        entity = ProviderModel::class,
        parentColumns = ["id_provider"],
        childColumns = ["id_provider"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ) val idProvider: Int
)