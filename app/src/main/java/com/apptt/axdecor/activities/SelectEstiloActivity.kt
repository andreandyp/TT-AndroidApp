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
            saveDatoEstilo("Barroco")
        }
        imClasico.setOnClickListener {
            saveDatoEstilo("Clasico")
        }
        imMinimalista.setOnClickListener {
            saveDatoEstilo("Minimalista")
        }
        imIndustrial.setOnClickListener {
            saveDatoEstilo("Industrial")
        }
        imModerno.setOnClickListener {
            saveDatoEstilo("Moderno")
        }
    }

    private fun saveDatoEstilo(estilo: String) {
        val sharPref = this.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        with(sharPref.edit()) {
            putString(getString(com.apptt.axdecor.R.string.style_key), estilo)
            commit()
        }
        val inten = Intent(this, ARElegirActivity::class.java)
        startActivity(inten)
    }
}
