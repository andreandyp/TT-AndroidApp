package com.apptt.axdecor.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Color(
    @ColumnInfo(name = "id_color") @PrimaryKey @NotNull val idColor: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rgb_code") val rgbCode: String
)