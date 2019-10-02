package com.apptt.axdecor.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.apptt.axdecor.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_datos2.*

class Datos2Fragment : Fragment(), AdapterView.OnItemSelectedListener{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var flecha = activity?.imgMenu
        flecha?.visibility = View.VISIBLE
        return inflater.inflate(R.layout.fragment_datos2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = Datos2FragmentArgs.fromBundle(arguments!!)
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.colores,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnColores.adapter = adapter
        }
        spnColores.onItemSelectedListener = this
        imColores.setOnClickListener {
            spnColores.performClick()
        }
        btnSiguiente.setOnClickListener {
            if (spnColores.selectedItemPosition.equals(0)) {
                Snackbar.make(btnSiguiente, "Parece que olvidas algo.", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
               view.findNavController().navigate(Datos2FragmentDirections.actionDatos2FragmentToDatos3Fragment(args.edad,args.nombre,spnColores.selectedItem.toString()))
            }
        }
    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.i("posicion",p2.toString())
        when(p2){
            0 -> {
                imColores.setImageResource(R.drawable.pantone)
                txtPersonalidad.text = "Selecciona un color..."
            }
            1 -> {
                imColores.setImageResource(R.drawable.red_circle)
                txtPersonalidad.text = "Positiva, Audaz, Firme, Enérgica; Competitiva, Exigente, Decidida, Con gran fuerza de voluntad, Con propósito."
            }
            2 -> {
                imColores.setImageResource(R.drawable.yellow_circle)
                txtPersonalidad.text = "Alegre, Inspiradora, Animada, Optimista; Sociable, Dinámica, Entusiasta, Persuasiva."
            }
            3 -> {
                imColores.setImageResource(R.drawable.green_circle)
                txtPersonalidad.text = "Callada, Tranquila, Serena, Conciliadora, Solícita, Que comparte, Paciente, Relajada."
            }
            4 -> {
                imColores.setImageResource(R.drawable.blue_circle)
                txtPersonalidad.text = "Imparcial, Objetiva, Analítica, Formal.  Prudente, Precisa, Reflexiva."
            }
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}
