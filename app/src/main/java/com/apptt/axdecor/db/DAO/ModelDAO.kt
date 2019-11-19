package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apptt.axdecor.db.Entities.*
import com.apptt.axdecor.db.queries.ModelWithCategoryModel

@Dao
interface ModelDAO {
    @Query("SELECT * FROM ModelModel")
    fun getAllModels(): List<ModelModel>

    @Query("SELECT * FROM ModelModel Where id_model = :id")
    suspend fun getModelById(id: Int): ModelModel

    @Query(
        """
        SELECT DISTINCT S.* FROM PredefinedStyleModel AS S
        INNER JOIN ModelHasPredefinedStyleModel AS MS
        ON S.id_predefined_style = MS.idPredefinedStyle
        WHERE MS.idModel = :idModel
    """
    )
    suspend fun viewStylesOfModel(idModel: Int): List<PredefinedStyleModel>

    @Query(
        """
        SELECT DISTINCT m.*, c.category, p.name AS proveedor
        FROM ModelModel m, ModelHasCategoryModel mc, ModelHasTypeModel mt, TypeModel t, CategoryModel c, ProviderModel p
        WHERE m.id_model = mc.idModel
            AND m.id_provider = p.id_provider
            AND m.id_model = mc.idModel
            AND mc.idCategory = c.id_category
            AND c.id_category = :idCategory
            AND m.id_model = mt.idModel
            AND mt.idType = t.id_type
            AND t.id_type = :idType
    """
    )
    suspend fun getModelsWithCategory(idCategory: Int, idType: Int): List<ModelWithCategoryModel>

    @Query("""
        SELECT DISTINCT m.*, c.category, p.name AS proveedor
        FROM ModelModel m, ModelHasCategoryModel mc, ModelHasTypeModel mt, ModelHasPredefinedStyleModel ms, TypeModel t, CategoryModel c, ProviderModel p, PredefinedStyleModel s
        WHERE m.id_model = mc.idModel
            AND m.id_provider = p.id_provider
            AND m.id_model = mc.idModel
            AND mc.idCategory = c.id_category
            AND c.id_category = :idCategory
            AND m.id_model = mt.idModel
            AND mt.idType = t.id_type
            AND t.id_type = :idType
            AND m.id_model = ms.idModel
            AND ms.idPredefinedStyle = s.id_predefined_style
            AND s.id_predefined_style = :idPredefinedStyle
    """)
    suspend fun getModelsWithStyle(idCategory: Int, idType: Int, idPredefinedStyle: Int): List<ModelWithCategoryModel>

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