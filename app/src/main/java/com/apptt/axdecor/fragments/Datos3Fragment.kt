package com.apptt.axdecor.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room

import com.apptt.axdecor.R
import com.apptt.axdecor.activities.TutorialConceptosActivity
import kotlinx.android.synthetic.main.fragment_datos3.*

class Datos3Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_datos3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = Datos3FragmentArgs.fromBundle(arguments!!)
        var habitacion :String
        Log.i("APPP", args.nombre)
        Log.i("APPP", args.personalidad)
        Log.i("APPP", args.edad)
        cardBano.setOnClickListener {
            saveDatosUsuario("Baño")
        }
        cardCocina.setOnClickListener {
            saveDatosUsuario("Cocina")
        }
        cardRecamara.setOnClickListener {
            saveDatosUsuario("Recamara")
        }
        cardComedor.setOnClickListener {
            saveDatosUsuario("Comedor")
        }
        cardSala.setOnClickListener {
            saveDatosUsuario("Sala")
        }
    }
    fun toSelectModeActivity(){
        var inten = Intent(activity?.applicationContext,TutorialConceptosActivity::class.java)
        startActivity(inten)
    }

    fun saveDatosUsuario(Habitacion:String){
        //TODO Guardar datos de usuario
        toSelectModeActivity()
    }
}