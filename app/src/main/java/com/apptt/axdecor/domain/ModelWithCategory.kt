package com.apptt.axdecor.domain

data class ModelWithCategory(
    val idModel: Int,
    val name: String,
    val fileAR: String,
    val price: String,
    val description: String?,
    val file2D: String?,
    val color: String,
    val medidas: String?,
    val codigo: String?,
    val category: String,
    val proveedor: String,
    val styles: List<String>,
    val idStyles: List<Int>,
    val idProvider: Int
)