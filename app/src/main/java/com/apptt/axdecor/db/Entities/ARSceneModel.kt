package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class ARSceneModel(
    @ColumnInfo(name = "id_arscene") @PrimaryKey @NonNull val idARScene: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "imagen") val imagen: String,
    @ForeignKey(
        entity = ProviderModel::class,
        parentColumns = ["id_provider"],
        childColumns = ["id_provider"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ) val idProvider: Int,
    @ForeignKey(
        entity = ProviderModel::class,
        parentColumns = ["id_type"],
        childColumns = ["id_type"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ) val idType: Int,
    @ForeignKey(
        entity = ProviderModel::class,
        parentColumns = ["id_predefined_style"],
        childColumns = ["id_predefined_style"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ) val idPredefinedStyle: Int
)