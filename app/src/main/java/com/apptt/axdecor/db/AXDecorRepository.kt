package com.apptt.axdecor.db

import android.app.Application
import com.apptt.axdecor.Network.*
import com.apptt.axdecor.db.DAO.ModelDAO
import com.apptt.axdecor.db.DAO.ProviderDAO
import com.apptt.axdecor.db.Entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AXDecorRepository(application: Application) {
    private val modeldao: ModelDAO
    private val providerDAO: ProviderDAO
    //private val listaModelos: List<ModelModel>

    init {
        val db = AXDecorDatabase.getDatabase(application)
        modeldao = db.modelDAO()
        providerDAO = db.providerDAO()
        //listaModelos = modeldao.getAllModels()
    }

    /*fun getModelos(): List<ModelModel> {
        return listaModelos
    }*/

    suspend fun deleteAllModelos() {
        modeldao.deleteAllModels()
    }

    suspend fun insertModel(modelModel: ModelModel) {
        modeldao.insertModel(modelModel)
    }

    suspend fun getProviders() {
        withContext(Dispatchers.IO) {
            val proveedores = AXDecorAPI.retrofitService.obtenerProveedoresAsync().await()
            providerDAO.insertProvider(*convertToProviderModel(proveedores))
            proveedores.forEach { proveedor ->
                providerDAO.addSocialNetworks(*extractSocialNetworks(proveedor.socialNetworks, proveedor.idProvider))
                providerDAO.addStores(*extractStores(proveedor.stores, proveedor.idProvider))
            }

        }
    }

    private fun convertToProviderModel(proveedores: List<NetworkProvider>) : Array<ProviderModel> {
        return proveedores.map {
            ProviderModel(
                idProvider = it.idProvider,
                name = it.name,
                rfc = it.rfc,
                razonSocial = it.razonSocial,
                persona = it.persona,
                rango = it.rango
            )
        }.toTypedArray()
    }

    private fun extractSocialNetworks(socialNetworks: List<NetworkSocialNetwork>, idProvider: Int) : Array<SocialNetworkModel> {
        return socialNetworks.map {
            SocialNetworkModel(
                idSocialNetwork = it.idSocialNetwork,
                socialNetworkURL = it.socialNetworkUrl,
                idProvider = idProvider
            )
        }.toTypedArray()
    }

    private fun extractStores(stores: List<NetworkStore>, idProvider: Int): Array<StoreModel> {
        return stores.map {
            StoreModel(
                idStore = it.idStore,
                address = it.address,
                phone = it.phone,
                email = it.email,
                idProvider = idProvider
            )
        }.toTypedArray()
    }

    private fun extractCategories(categories: List<NetworkCategory>, idProvider: Int): Array<ProviderHasCategoryModel> {
        return categories.map {
            ProviderHasCategoryModel(
                idProviderCategory = 0,
                idProvider = idProvider,
                idCategory = it.idCategory
            )
        }.toTypedArray()
    }

}