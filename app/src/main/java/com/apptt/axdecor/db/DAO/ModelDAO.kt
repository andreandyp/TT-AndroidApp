package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apptt.axdecor.db.Entities.Model

@Dao
interface ModelDAO {
    @Query("SELECT * from Model ORDER BY name")
    fun getAllModels(): List<Model>

    @Query("DELETE FROM Model")
    suspend fun deleteAllModels()

    @Insert
    suspend fun insertModel(model: Model)
}