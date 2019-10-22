package com.apptt.axdecor.database

import android.app.Application
import android.os.AsyncTask
import com.apptt.axdecor.database.DAO.ModelDAO
import com.apptt.axdecor.database.Entities.Model

class AXDecorRepository(application: Application) {
    private val modelDAO: ModelDAO
    private val listaModelos: List<Model>

    init {
        val axDecorDatabase = AXDecorDatabase.getDatabase(application)
        modelDAO = axDecorDatabase.modelDAO()
        listaModelos = modelDAO.getAllModels()
    }

    fun getAllModels() : List<Model>{
        return listaModelos
    }

    fun deleteAllModels(){
        modelDAO.deleteAllModels()
    }

    fun insertModel(model: Model){
        insertAsyncTask(modelDAO).execute(model)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: ModelDAO) : AsyncTask<Model, Void, Void>() {
        override fun doInBackground(vararg params: Model): Void? {
            mAsyncTaskDao.insertModel(params[0])
            return null
        }
    }

}