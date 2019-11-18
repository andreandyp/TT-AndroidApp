package com.apptt.axdecor.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.ModelWithCategory
import com.apptt.axdecor.domain.Paint
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

    init {
        viewModelScope.launch {
            _modelosConCategoria.value = mutableListOf()
            val lista = _modelosConCategoria.value
            for (id in 1..5) {
                val d = axDecorRepository.getModelsWithCategory(id)
                lista!!.add(d)
            }
            val pinturas = axDecorRepository.getPaints()
            lista!![3] = pinturas
            _listaModelos.value = _modelosConCategoria.value?.get(4)

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
        _listaModelos.value = _modelosConCategoria.value?.get(id)
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
}