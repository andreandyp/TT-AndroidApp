package com.apptt.axdecor.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatalogoViewModel : ViewModel() {

    private val _informacion = MutableLiveData<String>()
    val informacion: LiveData<String>
        get() = _informacion

    private val _descripcion = MutableLiveData<String>()
    val descripcion: LiveData<String>
        get() = _descripcion

    private val _precio = MutableLiveData<String>()
    val precio: LiveData<String>
        get() = _precio

    init {
        _informacion.value = "Sillón"
        _descripcion.value = "Sillón familiar de una pieza"
        _precio.value = "$250"
    }
}
