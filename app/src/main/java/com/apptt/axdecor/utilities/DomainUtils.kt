package com.apptt.axdecor.utilities

import com.apptt.axdecor.db.Entities.ModelModel
import com.apptt.axdecor.db.Entities.PaintModel
import com.apptt.axdecor.db.Entities.PredefinedStyleModel
import com.apptt.axdecor.db.Entities.ProviderModel
import com.apptt.axdecor.db.queries.ModelWithCategoryModel
import com.apptt.axdecor.domain.Model
import com.apptt.axdecor.domain.ModelWithCategory
import com.apptt.axdecor.domain.Paint
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
            idProvider = model.idProvider,
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
            idProvider = model.idProvider,
            category = ""
        )
    }

    fun convertToSingleProvider(provider: ProviderModel): Provider {
        return Provider(
            idProvider = provider.idProvider,
            name = provider.name,
            persona = provider.persona,
            rango = provider.rango,
            razonSocial = provider.razonSocial,
            rfc = provider.rfc
        )
    }


    fun convertToSinglePaint(paint:PaintModel) : Paint{
        return Paint(
            idPaint = paint.idPaint,
            name = paint.name,
            vendorCode = paint.vendorCode,
            rgbCode = paint.rgbCode,
            hexCode = paint.hexCode,
            presentacion = paint.presentacion,
            price = paint.price,
            idProvider = paint.idProvider,
            provider = ""
        )
    }

    private fun extractStyles(styles: List<PredefinedStyleModel>): List<String> {
        return styles.map {
            it.style
        }
    }

    fun convertToModelWithCategoryDomain(
        modelo: ModelWithCategoryModel,
        estilos: List<PredefinedStyleModel>
    ): ModelWithCategory {
        return ModelWithCategory(
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
            proveedor = modelo.proveedor,
            styles = estilos.map { it.style },
            idProvider = modelo.idProvider
        )
    }
}