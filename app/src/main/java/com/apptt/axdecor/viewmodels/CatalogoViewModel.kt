package com.apptt.axdecor.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CatalogoViewModel(application: Application) : AndroidViewModel(application) {

    private val _modelos = MutableLiveData<List<Model>>()
    val modelos: LiveData<List<Model>>
        get() = _modelos

    private val _verModelo = MutableLiveData<Model>()
    val verModelo: LiveData<Model>
        get() = _verModelo


    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val axDecorRepository = AXDecorRepository(application)

    init {
        viewModelScope.launch {
            val mods = axDecorRepository.getAllModels()
            _modelos.value = mods
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun verDetallesModelo(modelo: Model) {
        _verModelo.value = modelo
    }

    fun verDetallesModeloComplete(){
        _verModelo.value = null
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CatalogoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CatalogoViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
