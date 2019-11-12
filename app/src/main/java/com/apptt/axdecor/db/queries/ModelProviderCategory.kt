package com.apptt.axdecor.db.queries

import androidx.room.ColumnInfo
import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity
data class ModelProviderCategory(
    @ColumnInfo(name = "id_model") @NotNull val idModel: Int,
    @ColumnInfo(name = "id_provider") val idProvider: Int,
    @ColumnInfo(name = "id_category") val idCategory: Int,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "logo") val logo: String
)