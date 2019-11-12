package com.apptt.axdecor.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.Model
import kotlinx.coroutines.*
import java.text.NumberFormat
import java.util.*

class ARViewModel(application: Application) : AndroidViewModel(application) {
    // Datos del ViewModelo
    private val _listaModelos = MutableLiveData<List<Model>>()
    val listaModelos: LiveData<List<Model>>
        get() = _listaModelos

    private val _modelosConCategoria = MutableLiveData<MutableList<List<Model>>>()

    private val _verModelo = MutableLiveData<Model>()
    val verModelo: LiveData<Model>
        get() = _verModelo

    val modeloAR = MutableLiveData<Model>()

    val _modoDecoracion = MutableLiveData<String>()

    // Datos del modelo
    private val _estilosModelo = MutableLiveData<String>()
    val estilosModelo: LiveData<String>
        get() = _estilosModelo

    private val _precioFormateadoModelo = MutableLiveData<String>()
    val precioFormateadoModelo: LiveData<String>
        get() = _precioFormateadoModelo

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val axDecorRepository = AXDecorRepository(application)

    init {
        viewModelScope.launch {
            _modelosConCategoria.value = mutableListOf()
            for (id in 1..5) {
                val lista = _modelosConCategoria.value
                val d = axDecorRepository.getModelsWithCategory(id)
                lista!!.add(d)
            }
            _listaModelos.value = _modelosConCategoria.value?.get(4)
        }

        _modoDecoracion.value = "0"

        val nf = NumberFormat.getCurrencyInstance(Locale.US)
        _estilosModelo.value = _verModelo.value?.styles?.joinToString(", ")
        _precioFormateadoModelo.value = nf.format(_verModelo.value?.price?.toDouble() ?: 0.0)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun verModelosConCategoria(id: Int) {
        _listaModelos.value = _modelosConCategoria.value?.get(id)
    }

    fun verDetallesModelo(modelo: Model) {
        _verModelo.value = modelo
    }

    fun verDetallesModeloComplete(){
        _verModelo.value = null
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

    fun cambiarModoDecoracion(modo: String) {
        _modoDecoracion.value = modo
    }
}