package com.apptt.axdecor.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        val args3 = Datos3FragmentArgs.fromBundle(arguments!!)
        imBaño.setOnClickListener {
            saveDatosUsuario(1, args3.nombre, args3.edad, args3.personalidad)
        }
        imCocina.setOnClickListener {
            saveDatosUsuario(5, args3.nombre, args3.edad, args3.personalidad)
        }
        imRecamara.setOnClickListener {
            saveDatosUsuario(2, args3.nombre, args3.edad, args3.personalidad)
        }
        imComedor.setOnClickListener {
            saveDatosUsuario(3, args3.nombre, args3.edad, args3.personalidad)
        }
        imSala.setOnClickListener {
            saveDatosUsuario(4, args3.nombre, args3.edad, args3.personalidad)
        }
    }

    private fun saveDatosUsuario(
        Habitacion: Int,
        Nombre: String,
        Edad: String,
        Personalidad: String
    ) {
        val sharPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        with(sharPref.edit()) {
            putString(getString(R.string.user_Name), Nombre)
            putString(getString(R.string.color_key), Personalidad)
            putString(getString(R.string.age_key), Edad)
            putInt(getString(R.string.room_key), Habitacion)
            putInt(getString(R.string.anim1_key),0)
            putInt(getString(R.string.anim2_key),0)
            commit()
        }
        toSelectModeActivity()
    }

    private fun toSelectModeActivity() {
        val inten = Intent(activity?.applicationContext, TutorialConceptosActivity::class.java)
        startActivity(inten)
    }
}