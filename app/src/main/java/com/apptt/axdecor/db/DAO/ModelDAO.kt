package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apptt.axdecor.db.Entities.ModelHasCategoryModel
import com.apptt.axdecor.db.Entities.ModelHasPredefinedStyleModel
import com.apptt.axdecor.db.Entities.ModelModel

@Dao
interface ModelDAO {
    @Query("SELECT m.*, s.style from ModelModel m, ModelHasPredefinedStyleModel ms, PredefinedStyleModel s WHERE m.id_model = ms.idModel AND ms.idPredefinedStyle = s.id_predefined_style ORDER BY name")
    fun getAllModels(): List<ModelModel>

    @Query("DELETE FROM ModelModel")
    suspend fun deleteAllModels()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(vararg modelModel: ModelModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStyles(vararg modelHasPredefinedStyleModel: ModelHasPredefinedStyleModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(vararg modelHasCategoryModel: ModelHasCategoryModel)
}