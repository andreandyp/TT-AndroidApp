package com.apptt.axdecor.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.apptt.axdecor.R
import kotlinx.android.synthetic.main.activity_modo_decoracion.*

class ModoDecoracionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modo_decoracion)
        cardCrear.setOnClickListener {
            val intento = Intent(this, ARCrearActivity::class.java)
            startActivity(intento)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            )
        }
        cardElegir.setOnClickListener {
            val intento = Intent(this.applicationContext, ARElegirActivity::class.java)
            startActivity(intento)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            )
        }
    }
}
