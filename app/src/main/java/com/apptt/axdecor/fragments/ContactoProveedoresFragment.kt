package com.apptt.axdecor.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.apptt.axdecor.R
import com.apptt.axdecor.adapters.ContactosAdapter
import com.apptt.axdecor.db.queries.ModelProviderCategory
import kotlinx.android.synthetic.main.fragment_contacto_proveedores.*

class ContactoProveedoresFragment(private val tiendas: MutableList<MutableList<ModelProviderCategory>>) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacto_proveedores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (tiendas[4].size > 0) {
            val gridIluminacion = GridLayoutManager(activity, 1)
            recIluminacion.layoutManager = gridIluminacion
            recIluminacion.adapter = ContactosAdapter(activity!!, tiendas[4])
            tvIlumniacion.visibility = View.VISIBLE
            recIluminacion.visibility = View.VISIBLE
        }
        if(tiendas[2].size > 0){
            val gridMuebles = GridLayoutManager(activity, 1)
            recMuebles.layoutManager = gridMuebles
            recMuebles.adapter = ContactosAdapter(activity!!, tiendas[2])
            tvMuebles.visibility = View.VISIBLE
            recMuebles.visibility = View.VISIBLE
        }
        if(tiendas[0].size > 0){
            val gridAdornos = GridLayoutManager(activity, 1)
            recAdornos.layoutManager = gridAdornos
            recAdornos.adapter = ContactosAdapter(activity!!, tiendas[0])
            tvAdornos.visibility = View.VISIBLE
            recAdornos.visibility = View.VISIBLE
        }
        if(tiendas[1].size > 0){
            val gridPisos = GridLayoutManager(activity, 1)
            recPisos.layoutManager = gridPisos
            recPisos.adapter = ContactosAdapter(activity!!, tiendas[1])
            tvPisos.visibility = View.VISIBLE
            recPisos.visibility = View.VISIBLE

        }
        if(tiendas[3].size > 0){
            val gridPinturas = GridLayoutManager(activity, 1)
            recPinturas.layoutManager = gridPinturas
            recPinturas.adapter = ContactosAdapter(activity!!, tiendas[3])
            tvPinturas.visibility = View.VISIBLE
            recPinturas.visibility = View.VISIBLE
        }
    }


}
