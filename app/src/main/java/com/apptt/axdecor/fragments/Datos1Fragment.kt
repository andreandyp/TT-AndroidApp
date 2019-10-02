package com.apptt.axdecor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.apptt.axdecor.R
import com.apptt.axdecor.fragments.Datos1FragmentDirections.actionDatos1FragmentToDatos2Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_datos1.*

class Datos1Fragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var flecha = activity?.imgMenu
        flecha?.visibility = View.INVISIBLE
        return inflater.inflate(R.layout.fragment_datos1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nombre = etxtNombre as EditText
        ArrayAdapter.createFromResource(this.requireContext(),R.array.edades,android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnEdad.adapter = adapter
        }

        btnSiguiente.setOnClickListener {
            if (nombre.text.toString() == "" || spnEdad.selectedItemPosition == 0) {
                Snackbar.make(btnSiguiente, "Parece que olvidas algo.", Snackbar.LENGTH_SHORT).show()
            } else {
                it.findNavController().navigate(actionDatos1FragmentToDatos2Fragment(nombre.text.toString(), spnEdad.selectedItem.toString()))
            }
        }
    }
}
