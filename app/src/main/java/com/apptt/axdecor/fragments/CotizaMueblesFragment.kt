package com.apptt.axdecor.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.apptt.axdecor.R
import com.apptt.axdecor.adapters.CotizaMueblesAdapter
import com.apptt.axdecor.domain.Model
import kotlinx.android.synthetic.main.activity_galeria.*
import kotlinx.android.synthetic.main.fragment_cotiza_muebles.*

class CotizaMueblesFragment(
    private val modelo: MutableList<Model>,
    private val cantidades: HashMap<*, *>
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cotiza_muebles, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mGridLayoutManager = GridLayoutManager(activity, 1)
        recVMuebles.layoutManager = mGridLayoutManager
        recVMuebles.adapter = CotizaMueblesAdapter(activity!!, cantidades, modelo)
        tvTotal.text = "Total: $ ${getTotal(modelo,cantidades)}"
    }

    private fun getTotal(modelos: MutableList<Model>, cantidades: HashMap<*, *>): Float {
        var sum = 0f
        modelos.forEach {
            sum += it.price.toFloat() * cantidades[it.idModel] as Int
        }
        return sum
    }

}
