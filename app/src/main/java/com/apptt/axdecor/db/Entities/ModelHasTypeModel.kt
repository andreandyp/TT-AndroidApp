package com.apptt.axdecor.db.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelHasTypeModel(
    @PrimaryKey(autoGenerate = true) val idModelType: Long,
    val idModel: Int,
    val idType: Int
)