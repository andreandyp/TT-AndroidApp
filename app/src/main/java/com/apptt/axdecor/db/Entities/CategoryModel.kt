package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryModel(
    @ColumnInfo(name = "id_category") @PrimaryKey @NonNull val idCategory: Int,
    @ColumnInfo(name = "category") val category: String
)