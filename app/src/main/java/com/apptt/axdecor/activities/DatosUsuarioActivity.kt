package com.apptt.axdecor.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.apptt.axdecor.R

class DatosUsuarioActivity : AppCompatActivity() {
    lateinit var flechaAtras:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_usuario)
        flechaAtras = findViewById(R.id.imgMenu)
        flechaAtras.setOnClickListener {
            onBackPressed()
        }
    }
}
