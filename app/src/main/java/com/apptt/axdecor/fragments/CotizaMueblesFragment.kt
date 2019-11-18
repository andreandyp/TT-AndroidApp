package com.apptt.axdecor.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.apptt.axdecor.R
import com.apptt.axdecor.Utilities.TemplatePDF
import com.apptt.axdecor.adapters.CotizaMueblesAdapter
import com.apptt.axdecor.db.AXDecorRepository
import com.apptt.axdecor.domain.Model
import kotlinx.android.synthetic.main.fragment_cotiza_muebles.*
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CotizaMueblesFragment(
    private val modelo: MutableList<Model>,
    private val cantidades: HashMap<*, *>
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
        return inflater.inflate(R.layout.fragment_cotiza_muebles, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mGridLayoutManager = GridLayoutManager(activity, 1)
        recVMuebles.layoutManager = mGridLayoutManager
        recVMuebles.adapter = CotizaMueblesAdapter(activity!!, cantidades, modelo)
        val total = getTotal(modelo, cantidades)
        tvTotal.text = "Total: $ ${total}"
        btnPDF.setOnClickListener {
            var templatePDF = TemplatePDF(activity!!)
            templatePDF.openDocument()
            templatePDF.addMetaData("AXDecor", "Listado de modelos añadidos", "TT 2018-B056")
            templatePDF.addTitles(
                "AXDECOR",
                "Listado de modelos cotizados",
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            )
            templatePDF.addParagraph("Aqui te mostramos un listado con los modelos que escogiste en tu decoración.")
            templatePDF.addParagraph("Muestra esta hoja en tu tienda y pide los modelos mostrados.")
            templatePDF.createTable(
                arrayOf("Código", "Proveedor", "Nombre", "Cantidad", "Precio ($)"),
                getModelos(),total
            )
            templatePDF.closeDocument()
            templatePDF.verPDF(activity!!)
        }
    }

    private fun getTotal(modelos: MutableList<Model>, cantidades: HashMap<*, *>): Float {
        var sum = 0f
        modelos.forEach {
            sum += it.price.toFloat() * cantidades[it.idModel] as Int
        }
        return sum
    }

    private fun getModelos(): ArrayList<Array<String>> {
        var repo = AXDecorRepository(activity!!.application)
        var rows: ArrayList<Array<String>> = ArrayList()
        modelo.forEachIndexed { index, model ->
            runBlocking {
                rows.add(
                    arrayOf(
                        model.codigo!!,
                        repo.getProviderById(model.idProvider).name,
                        model.name,
                        cantidades[model.idModel].toString(),
                        model.price
                    )
                )
            }
        }
        return rows
    }

}
