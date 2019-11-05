package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apptt.axdecor.db.Entities.CategoryModel
import com.apptt.axdecor.db.Entities.ModelHasCategoryModel
import com.apptt.axdecor.db.Entities.ModelHasPredefinedStyleModel
import com.apptt.axdecor.db.Entities.ModelModel

@Dao
interface ModelDAO {
    @Query("SELECT * FROM ModelModel")
    fun getAllModels(): List<ModelModel>

    @Query("""
        SELECT DISTINCT C.* FROM CategoryModel AS C
        INNER JOIN ModelHasCategoryModel AS MC
        ON C.id_category = MC.idCategory
        WHERE MC.idModel = :idModel
    """)
    suspend fun viewCategoriesOfModel(idModel: Int): List<CategoryModel>

    @Query("DELETE FROM ModelModel")
    suspend fun deleteAllModels()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(vararg modelModel: ModelModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStyles(vararg modelHasPredefinedStyleModel: ModelHasPredefinedStyleModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(vararg modelHasCategoryModel: ModelHasCategoryModel)
}