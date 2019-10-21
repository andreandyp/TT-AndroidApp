package com.apptt.axdecor.database

import com.apptt.axdecor.database.DAO.AXDecorDao
import com.apptt.axdecor.database.Entities.Model

public class AXDecorRepository(private val axdecordao:AXDecorDao){
    val allModels:List<Model> = axdecordao.getAllModels()
}