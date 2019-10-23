package com.apptt.axdecor.db

import android.app.Application
import com.apptt.axdecor.db.DAO.ModelDAO
import com.apptt.axdecor.db.Entities.Model

class AXDecorRepository(application: Application) {
    private val modeldao: ModelDAO
    private val listaModelos: List<Model>

    init {
        val db = AXDecorDatabase.getDatabase(application)
        modeldao = db.modelDAO()
        listaModelos = modeldao.getAllModels()
    }

    fun getModelos(): List<Model> {
        return listaModelos
    }

    suspend fun deleteAllModelos() {
        modeldao.deleteAllModels()
    }

    suspend fun insertModel(model: Model) {
        modeldao.insertModel(model)
    }

}