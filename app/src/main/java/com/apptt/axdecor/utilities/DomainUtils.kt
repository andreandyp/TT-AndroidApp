package com.apptt.axdecor.utilities

import com.apptt.axdecor.db.Entities.ModelModel
import com.apptt.axdecor.db.Entities.PredefinedStyleModel
import com.apptt.axdecor.db.Entities.ProviderModel
import com.apptt.axdecor.domain.Model
import com.apptt.axdecor.domain.Provider

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
            idProvider = model.idProvider
        )
    }

    fun convertToSingleModelDomain(model:ModelModel):Model{
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
            idProvider = model.idProvider)
    }

    fun convertToSingleProvider(provider: ProviderModel):Provider{
        return Provider(
            idProvider = provider.idProvider,
            name = provider.name,
            persona = provider.persona,
            rango = provider.rango,
            razonSocial = provider.razonSocial,
            rfc = provider.rfc
        )
    }

    private fun extractStyles(styles: List<PredefinedStyleModel>): List<String> {
        return styles.map {
            it.style
        }
    }
}