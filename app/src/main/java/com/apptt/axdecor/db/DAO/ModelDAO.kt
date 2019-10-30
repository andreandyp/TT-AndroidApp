package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apptt.axdecor.db.Entities.ModelHasCategoryModel
import com.apptt.axdecor.db.Entities.ModelHasPredefinedStyleModel
import com.apptt.axdecor.db.Entities.ModelModel

@Dao
interface ModelDAO {
    @Query("SELECT * from ModelModel ORDER BY name")
    fun getAllModels(): List<ModelModel>

    @Query("DELETE FROM ModelModel")
    suspend fun deleteAllModels()

    @Insert
    suspend fun insertModel(vararg modelModel: ModelModel)

    @Insert
    suspend fun addStyles(vararg modelHasPredefinedStyleModel: ModelHasPredefinedStyleModel)

    @Insert
    suspend fun addCategories(vararg modelHasCategoryModel: ModelHasCategoryModel)
}