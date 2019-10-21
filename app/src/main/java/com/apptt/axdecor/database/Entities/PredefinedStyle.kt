package com.apptt.axdecor.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class PredefinedStyle(
    @ColumnInfo(name = "id_predefined_style") @PrimaryKey @NotNull val idPredefinedStyle: Int,
    @ColumnInfo(name = "style") val style: String
)