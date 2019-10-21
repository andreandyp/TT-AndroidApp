package com.apptt.axdecor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.apptt.axdecor.R

class SplashActivity : AppCompatActivity() {
    val splash = 500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val Intent = Intent(this, DatosUsuarioActivity::class.java)
            startActivity(Intent)
            finish()
        }, splash.toLong())
    }
}
