package com.apptt.axdecor.database.DAO

import androidx.room.Dao
import androidx.room.Query
import com.apptt.axdecor.database.Entities.Model

@Dao
interface AXDecorDao {
    @Query("SELECT * from Model ORDER BY name")
    fun getAllModels(): List<Model>
}