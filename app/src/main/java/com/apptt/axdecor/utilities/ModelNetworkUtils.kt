package com.apptt.axdecor.utilities

import com.apptt.axdecor.db.Entities.ModelHasCategoryModel
import com.apptt.axdecor.db.Entities.ModelHasPredefinedStyleModel
import com.apptt.axdecor.db.Entities.ModelHasTypeModel
import com.apptt.axdecor.db.Entities.ModelModel
import com.apptt.axdecor.network.NetworkCategory
import com.apptt.axdecor.network.NetworkModel
import com.apptt.axdecor.network.NetworkStyle
import com.apptt.axdecor.network.NetworkType

object ModelNetworkUtils {
    fun convertToModelModel(proveedores: List<NetworkModel>) : Array<ModelModel> {
        return proveedores.map {
            ModelModel(
                idModel = it.idModel,
                name = it.name,
                fileAR = it.fileAR,
                price = it.price,
                color = it.color,
                description = it.description,
                file2D = it.file2D,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                idProvider = it.Provider_idProvider,
                medidas = it.medidas,
                codigo = it.codigo
            )
        }.toTypedArray()
    }

    fun extractModelStyles(styles: List<NetworkStyle>, idModel: Int): Array<ModelHasPredefinedStyleModel> {
        return styles.map {
            ModelHasPredefinedStyleModel(
                idModelPredefinedStyle = 0,
                idModel = idModel,
                idPredefinedStyle = it.idPredefinedStyle
            )
        }.toTypedArray()
    }

    fun extractModelCategories(styles: List<NetworkCategory>, idModel: Int): Array<ModelHasCategoryModel> {
        return styles.map {
            ModelHasCategoryModel(
                idModelCategory = 0,
                idModel = idModel,
                idCategory = it.idCategory
            )
        }.toTypedArray()
    }

    fun extractModelTypes(types: List<NetworkType>, idModel: Int): Array<ModelHasTypeModel> {
        return types.map {
            ModelHasTypeModel(
                idModelType = 0,
                idModel = idModel,
                idType = it.idType
            )
        }.toTypedArray()
    }
}