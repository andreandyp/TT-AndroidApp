package com.apptt.axdecor.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.apptt.axdecor.R
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.CategoryProvider
import com.apptt.axdecor.domain.ModelWithCategory
import com.apptt.axdecor.domain.Paint
import com.apptt.axdecor.domain.ProveedorCatalogo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class ARViewModel(application: Application) : AndroidViewModel(application) {
    // Datos del ViewModel
    private val _listaModelos = MutableLiveData<List<Any>>()
    val listaModelos: LiveData<List<Any>>
        get() = _listaModelos

    private val _modelosConCategoria = MutableLiveData<MutableList<List<Any>>>()

    private val _verModelo = MutableLiveData<ModelWithCategory>()
    val verModelo: LiveData<ModelWithCategory>
        get() = _verModelo

    private val _verPintura = MutableLiveData<Paint>()
    val verPintura: LiveData<Paint> = _verPintura

    val modeloAR = MutableLiveData<ModelWithCategory>()
    val piso = MutableLiveData<ModelWithCategory>()
    val pinturaAR = MutableLiveData<Paint>()

    private val _detallesModelo = MutableLiveData<ModelWithCategory>()
    val detallesModelo: LiveData<ModelWithCategory>
        get() = _detallesModelo

    private val _detallesPintura = MutableLiveData<Paint>()
    val detallesPintura: LiveData<Paint> = _detallesPintura

    private val _modoDecoracion = MutableLiveData<Int>()
    val modoDecoracion: LiveData<Int>
        get() = _modoDecoracion

    // Datos del modelo
    private val _estilosModelo = MutableLiveData<String>()
    val estilosModelo: LiveData<String>
        get() = _estilosModelo

    private val _precioFormateadoModelo = MutableLiveData<String>()
    val precioFormateadoModelo: LiveData<String> = _precioFormateadoModelo

    private val _precioFormateadoPintura = MutableLiveData<String>()
    val precioFormateadoPintura: LiveData<String> = _precioFormateadoPintura

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val axDecorRepository = AXDecorRepository(application)

    private val _listaProveedores = MutableLiveData<List<ProveedorCatalogo>>()
    val listaProveedores: LiveData<List<ProveedorCatalogo>> = _listaProveedores

    private lateinit var proveedores: List<CategoryProvider>
    private lateinit var proveedoresNombre : List<String>
    private lateinit var proveedoresIds : List<Int>
    private var listaModelosConCategoria: List<Any>? = null

    init {
        val sharePref = application.getSharedPreferences(
            application.getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        )

        val habitacion = sharePref.getInt(application.getString(R.string.id_room_key), 0)
        viewModelScope.launch {
            proveedores = axDecorRepository.getProvidersByCategory()
            _modelosConCategoria.value = mutableListOf()
            val lista = _modelosConCategoria.value
            for (id in 1..5) {
                val d = axDecorRepository.getModelsWithCategory(id, habitacion)
                lista!!.add(d)
            }
            val pinturas = axDecorRepository.getPaints()
            lista!![3] = pinturas

            val listaProvs = mutableListOf<ProveedorCatalogo>()

            proveedoresIds = proveedores[4].idProviders
            proveedoresNombre = proveedores[4].providers
            for(i in proveedoresIds.indices) {
                listaProvs.add(
                    ProveedorCatalogo(proveedoresIds[i], proveedoresNombre[i])
                )
            }
            listaModelosConCategoria = _modelosConCategoria.value?.get(4)
            _listaModelos.value = listaModelosConCategoria
            _listaProveedores.value = listaProvs
        }

        _modoDecoracion.value = 0

        val nf = NumberFormat.getCurrencyInstance(Locale.US)
        _estilosModelo.value = _detallesModelo.value?.styles?.joinToString(", ")
        _precioFormateadoModelo.value = nf.format(_detallesModelo.value?.price?.toDouble() ?: 0.0)
        _precioFormateadoPintura.value = nf.format(_detallesPintura.value?.price?.toDouble() ?: 0.0)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun verModelosConCategoria(id: Int) {
        proveedoresNombre = proveedores[id].providers
        proveedoresIds = proveedores[id].idProviders
        val listaProvs = mutableListOf<ProveedorCatalogo>()
        for(i in proveedoresIds.indices) {
            listaProvs.add(
                ProveedorCatalogo(proveedoresIds[i], proveedoresNombre[i])
            )
        }
        listaModelosConCategoria = _modelosConCategoria.value?.get(id)
        _listaModelos.value = listaModelosConCategoria
        _listaProveedores.value = listaProvs
    }

    fun verDetallesModelo(modelo: ModelWithCategory) {
        _verModelo.value = modelo
    }

    fun verDetallesModeloComplete() {
        _detallesModelo.value = _verModelo.value
        _verModelo.value = null
    }

    fun verDetallesPintura(pintura: Paint) {
        _verPintura.value = pintura
    }

    fun verDetallesPinturaComplete() {
        _detallesPintura.value = _verPintura.value
        _verPintura.value = null
    }

    fun actualizarPrecio(precio: String) {
        val nf = NumberFormat.getCurrencyInstance(Locale.US)
        _precioFormateadoModelo.value = nf.format(precio.toDouble())
    }

    fun actualizarEstilos(estilos: List<String>) {
        _estilosModelo.value = estilos.joinToString(", ")
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ARViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ARViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun cambiarModoDecoracion(modo: Int) {
        _modoDecoracion.value = modo
    }

    fun verMueblesDe(idProvider: Int) {
        val modelosNuevos = listaModelosConCategoria
        if(modelosNuevos!!.isNotEmpty()) {
            _listaModelos.value = modelosNuevos.filter {

                if(it is Paint) {
                    it.idProvider == idProvider
                }
                else {
                    it as ModelWithCategory
                    it.idProvider == idProvider
                }

            }
        }

    }
}