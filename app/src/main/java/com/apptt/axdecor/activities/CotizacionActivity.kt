package com.apptt.axdecor.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apptt.axdecor.R
import com.apptt.axdecor.db.Entities.ModelModel

class CotizacionActivity() : AppCompatActivity() {
    lateinit var listaModelos: ArrayList<ModelModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizacion)
    }
}
