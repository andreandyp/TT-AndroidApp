package com.apptt.axdecor.db.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ARSceneHasModelModel(
    @PrimaryKey(autoGenerate = true) val idARSceneModel: Long,
    val idARScene: Int,
    val idModel: Int
)