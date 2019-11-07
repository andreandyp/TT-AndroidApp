package com.apptt.axdecor.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.apptt.axdecor.R
import com.apptt.axdecor.activities.ModoDecoracionActivity
import com.apptt.axdecor.adapters.ProveedoresAdapter
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.CategoryProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_datos1.*
import kotlinx.android.synthetic.main.fragment_proveedores.*
import kotlinx.android.synthetic.main.fragment_proveedores.btnSiguiente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProveedoresFragment : Fragment() {
    private val proveedoresSeleccionados: MutableList<String> = mutableListOf()
    private val callback = { idProveedor: Int, idCategory: Int, isChecked: Boolean ->
        if (isChecked) {
            proveedoresSeleccionados.add("$idProveedor,$idCategory")
        } else {
            proveedoresSeleccionados.remove("$idProveedor,$idCategory")
        }
        Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proveedores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSiguiente.setOnClickListener {
            if (proveedoresSeleccionados.size == 0){
                Snackbar.make(btnSiguiente, "Debes seleccionar al menos un proveedor.", Snackbar.LENGTH_SHORT).show()
            }else{
                val sharePref = activity?.getSharedPreferences(
                    getString(R.string.preference_file_key_datos),
                    Context.MODE_PRIVATE
                ) ?: return@setOnClickListener
                with(sharePref.edit()) {
                    putStringSet(getString(R.string.providers_key), proveedoresSeleccionados.toSet())
                }
                val mIntent = Intent(activity,ModoDecoracionActivity::class.java)
                startActivity(mIntent)
            }
        }
        proveedoresSeleccionados.clear()
        val repository = AXDecorRepository(activity!!.application)
        val gridIluminacion = GridLayoutManager(activity, 3)
        val gridMuebles = GridLayoutManager(activity, 3)
        val gridAdornos = GridLayoutManager(activity, 3)
        val gridPisos = GridLayoutManager(activity, 3)
        val gridPinturas = GridLayoutManager(activity, 3)
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
            recIluminacion.adapter =
                ProveedoresAdapter(proveedores.get(4), callback)
            recMuebles.adapter =
                ProveedoresAdapter(proveedores.get(2), callback)
            recAdornos.adapter =
                ProveedoresAdapter(proveedores.get(0), callback)
            recPisos.adapter =
                ProveedoresAdapter(proveedores.get(1), callback)
            recPinturas.adapter =
                ProveedoresAdapter(proveedores.get(3), callback)
        }
        recIluminacion.visibility = View.VISIBLE
        recMuebles.visibility = View.VISIBLE
        recAdornos.visibility = View.VISIBLE
        recPisos.visibility = View.VISIBLE
        recPinturas.visibility = View.VISIBLE
    }
}
