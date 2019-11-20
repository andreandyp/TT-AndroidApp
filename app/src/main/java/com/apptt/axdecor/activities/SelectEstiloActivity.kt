package com.apptt.axdecor.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apptt.axdecor.R
import kotlinx.android.synthetic.main.activity_select_estilo.*


class SelectEstiloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_estilo)
        imBarroco.setOnClickListener {
            saveDatoEstilo("Barroco", 2)
        }
        imClasico.setOnClickListener {
            saveDatoEstilo("Clasico", 6)
        }
        imMinimalista.setOnClickListener {
            saveDatoEstilo("Minimalista", 3)
        }
        imIndustrial.setOnClickListener {
            saveDatoEstilo("Industrial", 4)
        }
        imModerno.setOnClickListener {
            saveDatoEstilo("Moderno", 1)
        }
        imRustico.setOnClickListener {
            saveDatoEstilo("Rustico", 5)
        }
        imVintage.setOnClickListener {
            saveDatoEstilo("Vintage", 7)
        }
    }

    private fun saveDatoEstilo(estilo: String, id: Int) {
        val sharPref = this.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        with(sharPref.edit()) {
            putString(getString(R.string.style_key), estilo)
            putInt(getString(R.string.id_style_key), id)
            commit()
        }
        val inten = Intent(this, ARElegirActivity::class.java)
        startActivity(inten)
    }
}
