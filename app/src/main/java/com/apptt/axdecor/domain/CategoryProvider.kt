package com.apptt.axdecor.domain

data class CategoryProvider(
    val idCategory: Int,
    val category: String,
    val providers: List<String>,
    val idProviders: List<Int>
)