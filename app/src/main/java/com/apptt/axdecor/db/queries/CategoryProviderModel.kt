package com.apptt.axdecor.db.queries

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryProviderModel(
    @ColumnInfo(name = "id_category") @PrimaryKey @NonNull val idCategory: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "providers") val providers: String,
    @ColumnInfo(name = "idProviders") val idProviders: String,
    @ColumnInfo(name = "logos") val logos: String
)
