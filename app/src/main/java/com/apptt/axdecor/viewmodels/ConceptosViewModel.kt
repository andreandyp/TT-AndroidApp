package com.apptt.axdecor.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apptt.axdecor.R

class ConceptosViewModel(application: Application) : AndroidViewModel(application) {
    private var _tituloConcepto = MutableLiveData<String>()
    val tituloConcepto: LiveData<String>
        get() = _tituloConcepto

    private var _explicacionConcepto = MutableLiveData<String>()
    val explicacionConcepto: LiveData<String>
        get() = _explicacionConcepto

    val eventConceptoSeleccionado = MutableLiveData<Boolean>()

    var imagenX = 0.0f
    var imagenY = 0.0f
    private val conceptosTexto = listOf(
        "Necesitamos saber tu edad, tu nombre, tu personalidad y el presupuesto disponible.",
        "Puedes crear tu estilo propio o elegir un estilo previamente definido.",
        "Vamos a decorar el piso, los muros y los muebles. Podemos agregar lámparas y adornos si lo deseas.",
        "¿Cuánto cuesta toda la decoración? Lo calcularemos y te mostraremos la cotización final.",
        "¿Te gustó tu diseño? guárdalo y compártelo con tus amigos y familiares."
    )

    init {
        eventConceptoSeleccionado.value = false
    }

    fun onConceptoSelected(viewId: Int, viewX: Float, viewY: Float) {
        _tituloConcepto.value = when (viewId) {
            R.id.info_img -> getApplication<Application>().resources.getString(R.string.informacion)
            R.id.quiero_img -> getApplication<Application>().resources.getString(R.string.que_quiero)
            R.id.decoracion_img -> getApplication<Application>().resources.getString(R.string.decoracion)
            R.id.cotizacion_img -> getApplication<Application>().resources.getString(R.string.cotizacion)
            R.id.compartir_img -> getApplication<Application>().resources.getString(R.string.guardar_compartir)
            else -> "Hola"
        }


        _explicacionConcepto.value = when (viewId) {
            R.id.info_img -> conceptosTexto[0]
            R.id.quiero_img -> conceptosTexto[1]
            R.id.decoracion_img -> conceptosTexto[2]
            R.id.cotizacion_img -> conceptosTexto[3]
            R.id.compartir_img -> conceptosTexto[4]
            else -> "Hola"
        }

        eventConceptoSeleccionado.value = true
        imagenX = viewX
        imagenY = viewY
    }
}