package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProviderModel(
    @ColumnInfo(name = "id_provider") @PrimaryKey @NonNull val idProvider: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rfc") val rfc: String,
    @ColumnInfo(name = "razon_social") val razonSocial: String,
    @ColumnInfo(name = "persona") val persona: String,
    @ColumnInfo(name = "rango") val rango: String,
    @ColumnInfo(name = "logo") val logo: String

)