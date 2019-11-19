package com.apptt.axdecor.db

import android.app.Application
import com.apptt.axdecor.db.DAO.DataDAO
import com.apptt.axdecor.db.DAO.ModelDAO
import com.apptt.axdecor.db.DAO.PaintDAO
import com.apptt.axdecor.db.DAO.ProviderDAO
import com.apptt.axdecor.db.queries.ModelProviderCategory
import com.apptt.axdecor.domain.*
import com.apptt.axdecor.network.*
import com.apptt.axdecor.utilities.DataNetworkUtils.extractFullCategories
import com.apptt.axdecor.utilities.DataNetworkUtils.extractFullStyles
import com.apptt.axdecor.utilities.DataNetworkUtils.extractFullTypes
import com.apptt.axdecor.utilities.DomainUtils.convertToModelDomain
import com.apptt.axdecor.utilities.DomainUtils.convertToModelWithCategoryDomain
import com.apptt.axdecor.utilities.DomainUtils.convertToSingleModelDomain
import com.apptt.axdecor.utilities.DomainUtils.convertToSingleProvider
import com.apptt.axdecor.utilities.ModelNetworkUtils.convertToModelModel
import com.apptt.axdecor.utilities.ModelNetworkUtils.extractModelCategories
import com.apptt.axdecor.utilities.ModelNetworkUtils.extractModelStyles
import com.apptt.axdecor.utilities.ModelNetworkUtils.extractModelTypes
import com.apptt.axdecor.utilities.PaintNetworkUtils.convertToPaintModel
import com.apptt.axdecor.utilities.ProviderNetworkUtils.convertToProviderModel
import com.apptt.axdecor.utilities.ProviderNetworkUtils.extractProviderCategories
import com.apptt.axdecor.utilities.ProviderNetworkUtils.extractSocialNetworks
import com.apptt.axdecor.utilities.ProviderNetworkUtils.extractStores
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AXDecorRepository(application: Application) {
    private val modelDAO: ModelDAO
    private val providerDAO: ProviderDAO
    private val dataDAO: DataDAO
    private val paintDAO: PaintDAO

    init {
        val db = AXDecorDatabase.getDatabase(application)
        modelDAO = db.modelDAO()
        providerDAO = db.providerDAO()
        dataDAO = db.dataDAO()
        paintDAO = db.paintDAO()
    }

    suspend fun getModelsFromInternet(): List<NetworkModel> {
        return withContext(Dispatchers.IO) {
            AXDecorAPI.retrofitService.obtenerModelosAsync().await()
        }
    }

    suspend fun getAllModels(): List<Model> {
        return withContext(Dispatchers.IO) {
            val models = modelDAO.getAllModels()
            models.map { model ->
                val styles = modelDAO.viewStylesOfModel(model.idModel)
                convertToModelDomain(model, styles)
            }
        }
    }

    suspend fun getModelById(id: Int): Model {
        return withContext(Dispatchers.IO) {
            val modelo = modelDAO.getModelById(id)
            convertToSingleModelDomain(modelo)
        }
    }

    suspend fun getProviderById(id: Int): Provider {
        return withContext(Dispatchers.IO) {
            val provider = providerDAO.getProviderById(id)
            convertToSingleProvider(provider)
        }
    }

    suspend fun getStoresbyProviderId(idProvider: Int) : List<Store> {
        val lista = providerDAO.getStoresbyProviderId(idProvider)
        return withContext(Dispatchers.IO){
            lista.map {
                Store(
                    id_store = it.id_store,
                    email = it.email,
                    address = it.address,
                    id_provider = it.id_provider,
                    phone = it.phone,
                    logo = it.logo
                )
            }
        }
    }

    suspend fun getProviderByCategoryModel(): List<ModelProviderCategory> {
        return withContext(Dispatchers.IO) {
            providerDAO.getProviderByCategoryModel()
        }
    }

    suspend fun getProvidersByCategory(): List<CategoryProvider> {
        return withContext(Dispatchers.IO) {
            val providers = providerDAO.getProvidersByCategory()
            providers.map {
                CategoryProvider(
                    idCategory = it.idCategory,
                    category = it.category,
                    providers = it.providers.split(","),
                    idProviders = it.idProviders.split(",").map { id -> id.toInt() },
                    logos = it.logos.split(",")
                )
            }
        }
    }

    suspend fun saveModelsFromInternet(modelosNetwork: List<NetworkModel>) {
        withContext(Dispatchers.IO) {
            val modelosDB = convertToModelModel(modelosNetwork)
            modelDAO.insertModel(*modelosDB)

            modelosNetwork.forEach { modelo ->
                val stylesDB = extractModelStyles(modelo.styles, modelo.idModel)
                modelDAO.addStyles(*stylesDB)

                val categoriesDB = extractModelCategories(modelo.categories, modelo.idModel)
                modelDAO.addCategories(*categoriesDB)

                val typesDB = extractModelTypes(modelo.types, modelo.idModel)
                modelDAO.addTypes(*typesDB)
            }
        }
    }

    suspend fun getProvidersFromInternet(): List<NetworkProvider> {
        return withContext(Dispatchers.IO) {
            AXDecorAPI.retrofitService.obtenerProveedoresAsync().await()

        }
    }

    suspend fun saveProvidersFromInternet(proveedoresNetwork: List<NetworkProvider>) {
        withContext(Dispatchers.IO) {
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

    suspend fun getDefaultDataFromInternet(): NetworkDataContainer {
        return withContext(Dispatchers.IO) {
            AXDecorAPI.retrofitService.obtenerDatosAsync().await()
        }
    }

    suspend fun saveDefaultDataFromInternet(dataNetwork: NetworkDataContainer) {
        withContext(Dispatchers.IO) {
            val typesDB = extractFullTypes(dataNetwork.tipos)
            val stylesDB = extractFullStyles(dataNetwork.estilos)
            val categoriesDB = extractFullCategories(dataNetwork.categorias)

            dataDAO.insertTypes(*typesDB)
            dataDAO.insertStyles(*stylesDB)
            dataDAO.insertCategories(*categoriesDB)
        }
    }

    suspend fun getModelsWithCategory(idCategory: Int, idType: Int): List<ModelWithCategory> {
        return withContext(Dispatchers.IO) {
            val modelos = modelDAO.getModelsWithCategory(idCategory, idType)
            modelos.map {
                val estilos = modelDAO.viewStylesOfModel(it.idModel)
                convertToModelWithCategoryDomain(it, estilos)
            }
        }
    }

    suspend fun getPaintsFromInternet(): List<NetworkPaint> {
        return withContext(Dispatchers.IO) {
            AXDecorAPI.retrofitService.obtenerPinturasAsync().await()
        }
    }

    suspend fun savePaintFromInternet(paintsNetwork: List<NetworkPaint>) {
        val pinturasDB = convertToPaintModel(paintsNetwork)
        paintDAO.insertPaint(*pinturasDB)
    }

    suspend fun getPaints(): List<Paint> {
        val paintsDB = paintDAO.getPaints()
        return paintsDB.map {
            Paint(
                idPaint = it.idPaint,
                name = it.name,
                vendorCode = it.vendorCode,
                hexCode = it.hexCode,
                rgbCode = it.rgbCode,
                price = it.price,
                presentacion = it.presentacion,
                idProvider = it.idProvider,
                provider = it.proveedor
            )
        }
    }
}
