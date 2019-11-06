package com.apptt.axdecor.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.apptt.axdecor.R
import com.apptt.axdecor.adapters.ProveedoresAdapter
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.CategoryProvider
import kotlinx.android.synthetic.main.fragment_proveedores.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProveedoresFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proveedores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSiguiente.setOnClickListener {
            it.findNavController()
                .navigate(ProveedoresFragmentDirections.actionProveedoresFragmentToModoDecoracionFragment())
        }
        val repository = AXDecorRepository(activity!!.application)
        var gridIluminacion = GridLayoutManager(activity, 3)
        var gridMuebles = GridLayoutManager(activity, 3)
        var gridAdornos = GridLayoutManager(activity, 3)
        var gridPisos = GridLayoutManager(activity, 3)
        var gridPinturas = GridLayoutManager(activity, 3)
        recIluminacion.layoutManager = gridIluminacion
        recMuebles.layoutManager = gridMuebles
        recAdornos.layoutManager = gridAdornos
        recPisos.layoutManager = gridPisos
        recPinturas.layoutManager = gridPinturas
        val job = Job()
        val scope = CoroutineScope(job + Dispatchers.Main)
        lateinit var proveedores: List<CategoryProvider>
        scope.launch {
            proveedores = repository.getProvidersByCategory()
            recIluminacion.adapter = ProveedoresAdapter(activity!!.applicationContext,proveedores.get(4))
            recMuebles.adapter = ProveedoresAdapter(activity!!.applicationContext, proveedores.get(2).providers)
            recAdornos.adapter = ProveedoresAdapter(activity!!.applicationContext,proveedores.get(0).providers)
            recPisos.adapter = ProveedoresAdapter(activity!!.applicationContext,proveedores.get(1).providers)
            recPinturas.adapter = ProveedoresAdapter(activity!!.applicationContext,proveedores.get(3).providers)
        }
        recIluminacion.visibility = View.VISIBLE
        recMuebles.visibility = View.VISIBLE
        recAdornos.visibility = View.VISIBLE
        recPisos.visibility = View.VISIBLE
        recPinturas.visibility = View.VISIBLE
    }
}
