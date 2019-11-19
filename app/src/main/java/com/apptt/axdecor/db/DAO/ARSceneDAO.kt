package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.apptt.axdecor.db.Entities.ARSceneHasModelModel
import com.apptt.axdecor.db.Entities.ARSceneModel

@Dao
interface ARSceneDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScenes(vararg scenes: ARSceneModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addModels(vararg scenes: ARSceneHasModelModel)
}