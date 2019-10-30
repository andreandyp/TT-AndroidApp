package com.apptt.axdecor.Utilities

import com.apptt.axdecor.Network.NetworkCategory
import com.apptt.axdecor.Network.NetworkProvider
import com.apptt.axdecor.Network.NetworkSocialNetwork
import com.apptt.axdecor.Network.NetworkStore
import com.apptt.axdecor.db.Entities.ProviderHasCategoryModel
import com.apptt.axdecor.db.Entities.ProviderModel
import com.apptt.axdecor.db.Entities.SocialNetworkModel
import com.apptt.axdecor.db.Entities.StoreModel

object ProviderNetworkUtils  {
    fun convertToProviderModel(proveedores: List<NetworkProvider>) : Array<ProviderModel> {
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

    fun extractSocialNetworks(socialNetworks: List<NetworkSocialNetwork>, idProvider: Int) : Array<SocialNetworkModel> {
        return socialNetworks.map {
            SocialNetworkModel(
                idSocialNetwork = it.idSocialNetwork,
                socialNetworkURL = it.socialNetworkUrl,
                idProvider = idProvider
            )
        }.toTypedArray()
    }

    fun extractStores(stores: List<NetworkStore>, idProvider: Int): Array<StoreModel> {
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

    fun extractProviderCategories(categories: List<NetworkCategory>, idProvider: Int): Array<ProviderHasCategoryModel> {
        return categories.map {
            ProviderHasCategoryModel(
                idProviderCategory = 0,
                idProvider = idProvider,
                idCategory = it.idCategory
            )
        }.toTypedArray()
    }
}