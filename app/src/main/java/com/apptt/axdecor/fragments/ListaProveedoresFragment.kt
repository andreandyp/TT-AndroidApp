package com.apptt.axdecor.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager

import com.apptt.axdecor.R
import com.apptt.axdecor.adapters.ListaProveedoresAdapter
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.Provider
import com.apptt.axdecor.domain.Store
import kotlinx.android.synthetic.main.fragment_lista_proveedores.*
import kotlinx.coroutines.runBlocking
import java.util.*

class ListaProveedoresFragment : Fragment(),AdapterView.OnItemSelectedListener {
    var l = arrayListOf<String>()
    var ids = arrayListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_lista_proveedores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var repo = AXDecorRepository(activity!!.application)
        var listaNombres:List<Provider> = listOf()
        runBlocking {
            listaNombres = repo.getAllProviders()
        }
        listaNombres.forEach {
            l.add(it.name)
            ids.add(it.idProvider)
        }
        var comboAdapter = ArrayAdapter(activity!!,android.R.layout.simple_list_item_1,l)
        comboAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnProveedores.onItemSelectedListener = this
        spnProveedores.adapter = comboAdapter

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val repo = AXDecorRepository(activity!!.application)
        val seleccionado = ids[position]
        var tiendas:List<Store> = listOf()
        runBlocking {
            tiendas = repo.getStoresbyProviderId(seleccionado)
        }
        var mGridLayoutManager = GridLayoutManager(activity, 1)
        recProveedores.layoutManager = mGridLayoutManager
        recProveedores.adapter = ListaProveedoresAdapter(activity!!,tiendas)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
