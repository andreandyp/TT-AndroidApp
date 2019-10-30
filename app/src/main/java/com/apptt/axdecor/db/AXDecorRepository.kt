package com.apptt.axdecor.db

import android.app.Application
import com.apptt.axdecor.Network.AXDecorAPI
import com.apptt.axdecor.Utilities.DataNetworkUtils.extractFullCategories
import com.apptt.axdecor.Utilities.DataNetworkUtils.extractFullStyles
import com.apptt.axdecor.Utilities.DataNetworkUtils.extractFullTypes
import com.apptt.axdecor.Utilities.ModelNetworkUtils.convertToModelModel
import com.apptt.axdecor.Utilities.ModelNetworkUtils.extractModelCategories
import com.apptt.axdecor.Utilities.ModelNetworkUtils.extractModelStyles
import com.apptt.axdecor.Utilities.ProviderNetworkUtils.convertToProviderModel
import com.apptt.axdecor.Utilities.ProviderNetworkUtils.extractProviderCategories
import com.apptt.axdecor.Utilities.ProviderNetworkUtils.extractSocialNetworks
import com.apptt.axdecor.Utilities.ProviderNetworkUtils.extractStores
import com.apptt.axdecor.db.DAO.DataDAO
import com.apptt.axdecor.db.DAO.ModelDAO
import com.apptt.axdecor.db.DAO.ProviderDAO
import com.apptt.axdecor.db.Entities.ModelModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AXDecorRepository(application: Application) {
    private val modelDAO: ModelDAO
    private val providerDAO: ProviderDAO
    private val dataDAO: DataDAO

    init {
        val db = AXDecorDatabase.getDatabase(application)
        modelDAO = db.modelDAO()
        providerDAO = db.providerDAO()
        dataDAO = db.dataDAO()
    }

    suspend fun getModels() {
        withContext(Dispatchers.IO) {
            val modelosNetwork = AXDecorAPI.retrofitService.obtenerModelosAsync().await()

            val modelosDB = convertToModelModel(modelosNetwork)
            modelDAO.insertModel(*modelosDB)

            modelosNetwork.forEach { modelo ->
                val stylesDB = extractModelStyles(modelo.styles, modelo.idModel)
                modelDAO.addStyles(*stylesDB)

                val categoriesDB = extractModelCategories(modelo.categories, modelo.idModel)
                modelDAO.addCategories(*categoriesDB)
            }
        }
    }

    suspend fun deleteAllModelos() {
        modelDAO.deleteAllModels()
    }

    suspend fun insertModel(modelModel: ModelModel) {
        modelDAO.insertModel(modelModel)
    }

    suspend fun getProviders() {
        withContext(Dispatchers.IO) {
            val proveedoresNetwork = AXDecorAPI.retrofitService.obtenerProveedoresAsync().await()
            val proveedoresDB = convertToProviderModel(proveedoresNetwork)
            providerDAO.insertProvider(*proveedoresDB)

            proveedoresNetwork.forEach { proveedor ->
                val socialNetworksDB =
                    extractSocialNetworks(proveedor.socialNetworks, proveedor.idProvider)
                providerDAO.addSocialNetworks(*socialNetworksDB)

                val storesDB = extractStores(proveedor.stores, proveedor.idProvider)
                providerDAO.addStores(*storesDB)

                val categoryDB =
                    extractProviderCategories(proveedor.categories, proveedor.idProvider)
                providerDAO.addCategories(*categoryDB)
            }

        }
    }

    suspend fun getDefaultData() {
        withContext(Dispatchers.IO) {
            val dataNetwork = AXDecorAPI.retrofitService.obtenerDatosAsync().await()

            val typesDB = extractFullTypes(dataNetwork.tipos)
            val stylesDB = extractFullStyles(dataNetwork.estilos)
            val categoriesDB = extractFullCategories(dataNetwork.categorias)

            dataDAO.insertTypes(*typesDB)
            dataDAO.insertStyles(*stylesDB)
            dataDAO.insertCategories(*categoriesDB)
        }
    }
}
