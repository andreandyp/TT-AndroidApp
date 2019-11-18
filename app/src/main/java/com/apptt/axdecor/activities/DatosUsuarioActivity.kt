package com.apptt.axdecor.activities

import android.Manifest
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.apptt.axdecor.R

class DatosUsuarioActivity : AppCompatActivity() {
    lateinit var flechaAtras: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_usuario)
        flechaAtras = findViewById(R.id.imgMenu)
        flechaAtras.setOnClickListener {
            onBackPressed()
        }
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 1
        )
    }
}
