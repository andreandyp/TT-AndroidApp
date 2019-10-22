package com.apptt.axdecor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.apptt.axdecor.R
import com.apptt.axdecor.database.AXDecorDatabase
import com.apptt.axdecor.database.AXDecorRepository
import com.apptt.axdecor.database.Entities.Model

class SplashActivity : AppCompatActivity() {
    private val splash = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val repo = AXDecorRepository(application)
        repo.insertModel(Model("1","Prueba1","Mueble","Baño","qqqq","125.00","LELE","qqq"))
        Log.i("TESTDB",repo.getAllModels().toString())
        Handler().postDelayed({
            val Intent = Intent(this, DatosUsuarioActivity::class.java)
            startActivity(Intent)
            finish()
        }, splash.toLong())
    }
}
