package com.apptt.axdecor.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatosUsuarioEntity(
    @PrimaryKey val userId: Int,
    @ColumnInfo(name = "Edad") val edad: String?,
    @ColumnInfo(name = "ColorFav") val color: String?,
    @ColumnInfo(name = "Personalidad") val personalidad: String?
)
