package com.apptt.axdecor.db.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelHasPredefinedStyleModel(
    @PrimaryKey(autoGenerate = true) val idModelPredefinedStyle: Long,
    val idModel: Int,
    val idPredefinedStyle: Int
)