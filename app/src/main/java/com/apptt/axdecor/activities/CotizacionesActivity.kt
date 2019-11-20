package com.apptt.axdecor.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.apptt.axdecor.R
import com.apptt.axdecor.adapters.CotizacionesPagerAdapter
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.db.queries.ModelProviderCategory
import com.apptt.axdecor.domain.Model
import com.apptt.axdecor.domain.Paint
import com.apptt.axdecor.domain.Provider
import com.apptt.axdecor.domain.Store
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.coroutines.runBlocking

class CotizacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizaciones)
        val cantidades = intent.extras?.getSerializable("modelos") as HashMap<*, *>
        val pintura = intent.extras?.getParcelable<Paint>("pintura")
        var pinturaProvs: List<Store> = listOf()
        val repository = AXDecorRepository(this.application)
        if(pintura != null){
            runBlocking {
                pinturaProvs = repository.getStoresbyProviderId(pintura!!.idProvider)
            }
        }
        val modelos = getDataModels(cantidades)
        val proveedores = getProvidersCategoryFromModels(modelos)
        val cotizacionesPagerAdapter =
            CotizacionesPagerAdapter(this, supportFragmentManager, cantidades, modelos, proveedores, pintura, pinturaProvs)
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
        val repository = AXDecorRepository(this.application)
        runBlocking {
            modelosClaves.forEach {
                lista.add(repository.getModelById(it.key as Int))
            }
        }
        return lista
    }

    private fun getProvidersCategoryFromModels(modelos: MutableList<Model>): MutableList<MutableList<ModelProviderCategory>> {
        val repository = AXDecorRepository(this.application)
        val listaCategorias = mutableListOf<MutableList<ModelProviderCategory>>()
        var decoracion = mutableListOf<ModelProviderCategory>()
        var pisos = mutableListOf<ModelProviderCategory>()
        var muebles = mutableListOf<ModelProviderCategory>()
        var pintura = mutableListOf<ModelProviderCategory>()
        var iluminacion = mutableListOf<ModelProviderCategory>()
        runBlocking {
            val prov = repository.getProviderByCategoryModel()
            modelos.forEach { itModel ->
                prov.forEach {
                    if (itModel.idModel == it.idModel) {
                        when (it.idCategory) {
                            1 -> {
                                decoracion.add(it)
                            }
                            2 -> {
                                pisos.add(it)
                            }
                            3 -> {
                                muebles.add(it)
                            }
                            4 -> {
                                pintura.add(it)
                            }
                            5 -> {
                                iluminacion.add(it)
                            }
                        }
                    }
                }
            }
            listaCategorias.add(decoracion)
            listaCategorias.add(pisos)
            listaCategorias.add(muebles)
            listaCategorias.add(pintura)
            listaCategorias.add(iluminacion)
        }
        return listaCategorias
    }

}