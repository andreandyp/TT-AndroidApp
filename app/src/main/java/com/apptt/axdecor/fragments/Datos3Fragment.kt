package com.apptt.axdecor.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.apptt.axdecor.R
import com.apptt.axdecor.activities.TutorialConceptosActivity
import kotlinx.android.synthetic.main.fragment_datos3.*

class Datos3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_datos3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imBaño.setOnClickListener {
            saveDatosUsuario(1,"Baño")
        }
        imCocina.setOnClickListener {
            saveDatosUsuario(5,"Cocina")
        }
        imRecamara.setOnClickListener {
            saveDatosUsuario(2,"Recámara")
        }
        imComedor.setOnClickListener {
            saveDatosUsuario(3,"Comedor")
        }
        imSala.setOnClickListener {
            saveDatosUsuario(4,"Sala")
        }
    }

    private fun saveDatosUsuario(idHabitacion: Int,nombrehabitacion:String) {
        val sharPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        with(sharPref.edit()) {
            putInt(getString(R.string.id_room_key), idHabitacion)
            putString(getString(R.string.room_key), nombrehabitacion)
            commit()
        }
        view!!.findNavController().navigate(Datos3FragmentDirections.actionDatos3FragmentToProveedoresFragment())
    }

}