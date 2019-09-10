package com.apptt.axdecor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.apptt.axdecor.activities.DatosUsuarioActivity
import com.apptt.axdecor.fragments.Datos1Fragment

class splashActivity : AppCompatActivity() {
    val splash = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val Intent = Intent(this,DatosUsuarioActivity::class.java)
            startActivity(Intent)
            finish()
        }, splash.toLong())
    }
}
