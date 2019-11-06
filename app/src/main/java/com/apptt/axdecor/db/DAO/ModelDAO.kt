package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apptt.axdecor.db.Entities.*

@Dao
interface ModelDAO {
    @Query("SELECT * FROM ModelModel")
    fun getAllModels(): List<ModelModel>

    @Query("""
        SELECT DISTINCT S.* FROM PredefinedStyleModel AS S
        INNER JOIN ModelHasPredefinedStyleModel AS MS
        ON S.id_predefined_style = MS.idPredefinedStyle
        WHERE MS.idModel = :idModel
    """)
    suspend fun viewStylesOfModel(idModel: Int): List<PredefinedStyleModel>

    @Query("DELETE FROM ModelModel")
    suspend fun deleteAllModels()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(vararg modelModel: ModelModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStyles(vararg modelHasPredefinedStyleModel: ModelHasPredefinedStyleModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(vararg modelHasCategoryModel: ModelHasCategoryModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTypes(vararg modelHasTypeModel: ModelHasTypeModel)
}