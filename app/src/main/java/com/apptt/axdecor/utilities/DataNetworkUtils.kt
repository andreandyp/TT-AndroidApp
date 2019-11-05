package com.apptt.axdecor.utilities

import com.apptt.axdecor.network.NetworkFullCategory
import com.apptt.axdecor.network.NetworkFullStyle
import com.apptt.axdecor.network.NetworkFullType
import com.apptt.axdecor.db.Entities.CategoryModel
import com.apptt.axdecor.db.Entities.PredefinedStyleModel
import com.apptt.axdecor.db.Entities.TypeModel

object DataNetworkUtils {
    fun extractFullTypes(proveedores: List<NetworkFullType>) : Array<TypeModel> {
        return proveedores.map {
            TypeModel(
                idType = it.idType,
                nameType = it.nameType
            )
        }.toTypedArray()
    }

    fun extractFullStyles(proveedores: List<NetworkFullStyle>) : Array<PredefinedStyleModel> {
        return proveedores.map {
            PredefinedStyleModel(
                idPredefinedStyle = it.idPredefinedStyle,
                style = it.style
            )
        }.toTypedArray()
    }

    fun extractFullCategories(proveedores: List<NetworkFullCategory>) : Array<CategoryModel> {
        return proveedores.map {
            CategoryModel(
                idCategory = it.idCategory,
                category = it.category
            )
        }.toTypedArray()
    }
}