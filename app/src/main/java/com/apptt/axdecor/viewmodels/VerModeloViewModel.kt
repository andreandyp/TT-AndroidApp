package com.apptt.axdecor.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.apptt.axdecor.domain.Model

class VerModeloViewModel(model: Model, app: Application) : AndroidViewModel(app) {
    private val _modelo = MutableLiveData<Model>()
    val modelo: LiveData<Model>
        get() = _modelo

    init {
        _modelo.value = model
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
