package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apptt.axdecor.db.Entities.ProviderHasCategoryModel
import com.apptt.axdecor.db.Entities.ProviderModel
import com.apptt.axdecor.db.Entities.SocialNetworkModel
import com.apptt.axdecor.db.Entities.StoreModel
import com.apptt.axdecor.db.queries.CategoryProviderModel
import com.apptt.axdecor.db.queries.ModelProviderCategory

@Dao
interface ProviderDAO {
    @Query("SELECT * FROM ProviderModel ORDER BY name")
    fun getAllProviders(): List<ProviderModel>

    @Query("SELECT * FROM ProviderModel where id_provider = :id")
    suspend fun getProviderById(id: Int): ProviderModel

    @Query(
        """
        SELECT c.id_category, c.category, GROUP_CONCAT(DISTINCT p.logo) AS logos, GROUP_CONCAT(DISTINCT p.name) AS providers, GROUP_CONCAT(DISTINCT p.id_provider) AS idProviders
        FROM ProviderModel AS p
        JOIN ProviderHasCategoryModel AS pc
        ON p.id_provider = pc.idProvider
        JOIN CategoryModel AS c
        ON pc.idCategory = c.id_category
        GROUP BY c.category, c.id_category
        ORDER BY c.id_category
    """
    )
    suspend fun getProvidersByCategory(): List<CategoryProviderModel>

    @Query(
        """ 
            SELECT DISTINCT a.id_model,b.id_provider,c.id_category,e.address,e.phone,e.email,b.logo 
            FROM ModelModel as a,ProviderModel as b,CategoryModel as c,ModelHasCategoryModel as d, StoreModel as e 
            WHERE a.id_provider = b.id_provider and a.id_model = d.idModel and c.id_category = d.idCategory and b.id_provider = e.id_provider
            """
    )
    suspend fun getProviderByCategoryModel(): List<ModelProviderCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvider(vararg provider: ProviderModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSocialNetworks(vararg socialNetworks: SocialNetworkModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStores(vararg stores: StoreModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(vararg categories: ProviderHasCategoryModel)
}