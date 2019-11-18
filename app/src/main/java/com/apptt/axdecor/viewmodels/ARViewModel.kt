package com.apptt.axdecor.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.Model
import com.apptt.axdecor.domain.ModelWithCategory
import kotlinx.coroutines.*
import java.text.NumberFormat
import java.util.*

class ARViewModel(application: Application) : AndroidViewModel(application) {
    // Datos del ViewModel
    private val _listaModelos = MutableLiveData<List<ModelWithCategory>>()
    val listaModelos: LiveData<List<ModelWithCategory>>
        get() = _listaModelos

    private val _modelosConCategoria = MutableLiveData<MutableList<List<ModelWithCategory>>>()

    private val _verModelo = MutableLiveData<ModelWithCategory>()
    val verModelo: LiveData<ModelWithCategory>
        get() = _verModelo

    val modeloAR = MutableLiveData<ModelWithCategory>()
    val piso = MutableLiveData<ModelWithCategory>()

    private val _detallesModelo = MutableLiveData<ModelWithCategory>()
    val detallesModelo: LiveData<ModelWithCategory>
        get() = _detallesModelo

    private val _modoDecoracion = MutableLiveData<Int>()
    val modoDecoracion: LiveData<Int>
        get() = _modoDecoracion

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

        _modoDecoracion.value = 0

        val nf = NumberFormat.getCurrencyInstance(Locale.US)
        _estilosModelo.value = _detallesModelo.value?.styles?.joinToString(", ")
        _precioFormateadoModelo.value = nf.format(_detallesModelo.value?.price?.toDouble() ?: 0.0)
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

    fun verDetallesModeloComplete(){
        _detallesModelo.value = _verModelo.value
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

    fun cambiarModoDecoracion(modo: Int) {
        _modoDecoracion.value = modo
    }
}