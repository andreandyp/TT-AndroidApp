package com.apptt.axdecor.utilities

import android.util.Log
import com.apptt.axdecor.db.Entities.CategoryModel
import com.apptt.axdecor.db.Entities.ModelModel
import com.apptt.axdecor.domain.Model

object DomainUtils {
    fun convertToModelDomain(model: ModelModel, categories: List<CategoryModel>): Model {
        return Model(
            idModel = model.idModel,
            name = model.name,
            price = model.price,
            description = model.description,
            color = model.color,
            fileAR = model.fileAR,
            file2D = model.file2D,
            categories = extractCategories(categories)
        )
    }

    private fun extractCategories(categories: List<CategoryModel>): List<String> {
        return categories.map {
            it.category
        }
    }
}