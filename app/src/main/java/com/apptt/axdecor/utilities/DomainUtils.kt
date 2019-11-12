package com.apptt.axdecor.utilities

import com.apptt.axdecor.db.Entities.ModelModel
import com.apptt.axdecor.db.Entities.PredefinedStyleModel
import com.apptt.axdecor.db.queries.ModelWithCategoryModel
import com.apptt.axdecor.domain.Model
import com.apptt.axdecor.domain.ModelWithCategory

object DomainUtils {
    fun convertToModelDomain(model: ModelModel, styles: List<PredefinedStyleModel>): Model {
        return Model(
            idModel = model.idModel,
            name = model.name,
            price = model.price,
            description = model.description,
            color = model.color,
            fileAR = model.fileAR,
            file2D = model.file2D,
            styles = extractStyles(styles),
            medidas = model.medidas,
            codigo = model.codigo,
            category = ""
        )
    }

    fun convertToSingleModelDomain(model: ModelModel): Model {
        return Model(
            idModel = model.idModel,
            name = model.name,
            price = model.price,
            description = model.description,
            color = model.color,
            fileAR = model.fileAR,
            file2D = model.file2D,
            styles = emptyList(),
            medidas = model.medidas,
            codigo = model.codigo,
            category = ""
        )
    }

    private fun extractStyles(styles: List<PredefinedStyleModel>): List<String> {
        return styles.map {
            it.style
        }
    }

    fun convertToModelWithCategoryDomain(modelo: ModelWithCategoryModel): Model {
        return Model(
            idModel = modelo.idModel,
            name = modelo.name,
            price = modelo.price,
            description = modelo.description ?: "",
            color = modelo.color,
            fileAR = modelo.fileAR,
            file2D = modelo.file2D,
            medidas = modelo.medidas,
            codigo = modelo.codigo,
            category = modelo.category,
            styles = emptyList()
            )
    }
}