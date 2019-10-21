package com.apptt.axdecor.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class ARScene(
    @ColumnInfo(name = "id_arscene") @PrimaryKey @NotNull val idARScene: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "id_user") val idUser: Int
)