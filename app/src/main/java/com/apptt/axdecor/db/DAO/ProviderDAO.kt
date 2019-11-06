package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apptt.axdecor.db.Entities.*
import com.apptt.axdecor.db.queries.CategoryProviderModel

@Dao
interface ProviderDAO {
    @Query("SELECT * FROM ProviderModel ORDER BY name")
    fun getAllProviders(): List<ProviderModel>

    @Query(
        """
        SELECT c.id_category, c.category, GROUP_CONCAT(DISTINCT p.name) AS providers, GROUP_CONCAT(DISTINCT p.id_provider) AS idProviders
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvider(vararg provider: ProviderModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSocialNetworks(vararg socialNetworks: SocialNetworkModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStores(vararg stores: StoreModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(vararg categories: ProviderHasCategoryModel)
}