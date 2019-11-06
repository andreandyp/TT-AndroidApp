package com.apptt.axdecor.utilities

import android.util.Log
import com.apptt.axdecor.db.Entities.CategoryModel
import com.apptt.axdecor.db.Entities.ModelModel
import com.apptt.axdecor.db.Entities.PredefinedStyleModel
import com.apptt.axdecor.domain.Model

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
            styles = extractStyles(styles)
        )
    }

    private fun extractStyles(styles: List<PredefinedStyleModel>): List<String> {
        return styles.map {
            it.style
        }
    }
}