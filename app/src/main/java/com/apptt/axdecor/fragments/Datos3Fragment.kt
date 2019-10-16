package com.apptt.axdecor.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apptt.axdecor.R
import com.apptt.axdecor.activities.TutorialConceptosActivity
import kotlinx.android.synthetic.main.fragment_datos3.*

class Datos3Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_datos3, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args3 = Datos3FragmentArgs.fromBundle(arguments!!)
        var habitacion :String
        Log.i("APPP", args3.nombre)
        Log.i("APPP", args3.personalidad)
        Log.i("APPP", args3.edad)
        cardBano.setOnClickListener {
            saveDatosUsuario("Baño",args3.nombre,args3.edad,args3.personalidad)
        }
        cardCocina.setOnClickListener {
            saveDatosUsuario("Cocina",args3.nombre,args3.edad,args3.personalidad)
        }
        cardRecamara.setOnClickListener {
            saveDatosUsuario("Recamara",args3.nombre,args3.edad,args3.personalidad)
        }
        cardComedor.setOnClickListener {
            saveDatosUsuario("Comedor",args3.nombre,args3.edad,args3.personalidad)
        }
        cardSala.setOnClickListener {
            saveDatosUsuario("Sala",args3.nombre,args3.edad,args3.personalidad)
        }
    }

    private fun saveDatosUsuario(Habitacion:String, Nombre:String, Edad:String, Personalidad:String){
        val sharPref = activity?.getSharedPreferences(getString(R.string.preference_file_key_datos),Context.MODE_PRIVATE)?:return
        with (sharPref.edit()){
            putString(getString(R.string.user_Name),Nombre)
            putString(getString(R.string.color_key),Personalidad)
            putString(getString(R.string.age_key),Edad)
            putString(getString(R.string.room_key),Habitacion)
            commit()
        }
        toSelectModeActivity()
    }

    private fun toSelectModeActivity(){
        val inten = Intent(activity?.applicationContext,TutorialConceptosActivity::class.java)
        startActivity(inten)
    }
}