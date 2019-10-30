package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PredefinedStyleModel(
    @ColumnInfo(name = "id_predefined_style") @PrimaryKey @NonNull val idPredefinedStyle: Int,
    @ColumnInfo(name = "style") val style: String
)