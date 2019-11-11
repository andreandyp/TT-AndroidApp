package com.apptt.axdecor.activities

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.apptt.axdecor.R
import com.apptt.axdecor.adapters.CotizacionesPagerAdapter
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.Model
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.coroutines.*

class CotizacionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizaciones)
        val cantidades = intent.extras?.getSerializable("modelos") as HashMap<*, *>
        val modelos = getDataModels(cantidades)
        val cotizacionesPagerAdapter = CotizacionesPagerAdapter(this, supportFragmentManager,cantidades,modelos)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = cotizacionesPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val atras = findViewById<ImageView>(R.id.imgMenu)
        tvTituloAccion.text = "Cotización"
        atras.setOnClickListener {
            onBackPressed()
        }

    }

    private fun getDataModels(modelosClaves: HashMap<*, *>): MutableList<Model> {
        val lista = mutableListOf<Model>()
        val repository = AXDecorRepository(application)
        runBlocking {
            modelosClaves.forEach {
                lista.add(repository.getModelById(it.key as Int))
            }
        }
        return lista
    }
}