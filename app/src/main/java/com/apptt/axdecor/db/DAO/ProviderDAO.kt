package com.apptt.axdecor.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apptt.axdecor.db.Entities.ProviderHasCategoryModel
import com.apptt.axdecor.db.Entities.ProviderModel
import com.apptt.axdecor.db.Entities.SocialNetworkModel
import com.apptt.axdecor.db.Entities.StoreModel

@Dao
interface ProviderDAO {
    @Query("SELECT * FROM ProviderModel ORDER BY name")
    fun getAllProviders(): List<ProviderModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvider(vararg provider: ProviderModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSocialNetworks(vararg socialNetworks: SocialNetworkModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStores(vararg stores: StoreModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(vararg categories: ProviderHasCategoryModel)
}