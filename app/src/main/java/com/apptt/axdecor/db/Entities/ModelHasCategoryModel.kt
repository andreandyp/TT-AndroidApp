package com.apptt.axdecor.db.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelHasCategoryModel(
    @PrimaryKey(autoGenerate = true) val idModelCategory: Long,
    val idModel: Int,
    val idCategory: Int
)