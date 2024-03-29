package com.apptt.axdecor.db.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProviderHasCategoryModel(
    @PrimaryKey(autoGenerate = true) val idProviderCategory: Long,
    val idProvider: Int,
    val idCategory: Int
)