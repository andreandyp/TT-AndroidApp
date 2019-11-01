package com.apptt.axdecor.utilities

import com.apptt.axdecor.db.Entities.ModelModel
import com.apptt.axdecor.domain.Model

object DomainUtils {
    fun convertToModelDomain(modelos: List<ModelModel>): List<Model> {
        return modelos.map {
            Model(
                idModel = it.idModel,
                name = it.name,
                price = it.price,
                description = it.description,
                color = it.color,
                fileAR = it.fileAR,
                file2D = it.file2D
            )
        }
    }
}