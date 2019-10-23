package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ARScene(
    @ColumnInfo(name = "id_arscene") @PrimaryKey @NonNull val idARScene: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "id_user") val idUser: Int
)