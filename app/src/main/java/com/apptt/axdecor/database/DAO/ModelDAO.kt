package com.apptt.axdecor.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apptt.axdecor.database.Entities.Model

@Dao
interface ModelDAO {
    @Query("SELECT * from Model ORDER BY name")
    fun getAllModels(): List<Model>

    @Query("DELETE FROM Model")
    fun deleteAllModels()

    @Insert
    fun insertModel(model: Model)
}