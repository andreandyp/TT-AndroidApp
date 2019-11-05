package com.apptt.axdecor.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.apptt.axdecor.domain.Model
import java.text.NumberFormat
import java.util.*

class VerModeloViewModel(model: Model, app: Application) : AndroidViewModel(app) {
    private val _modelo = MutableLiveData<Model>()
    val modelo: LiveData<Model>
        get() = _modelo

    private val _estilos = MutableLiveData<String>()
    val estilos: LiveData<String>
        get() = _estilos

    private val _precioFormateado = MutableLiveData<String>()
    val precioFormateado: LiveData<String>
        get() = _precioFormateado

    init {
        val nf = NumberFormat.getCurrencyInstance(Locale.US)
        _modelo.value = model
        _estilos.value = model.categories.joinToString(", ")
        _precioFormateado.value = nf.format(model.price.toDouble())
    }

    class Factory(
        private val model: Model,
        private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VerModeloViewModel::class.java)) {
                return VerModeloViewModel(model, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
