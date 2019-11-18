package com.apptt.axdecor.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apptt.axdecor.R
import com.apptt.axdecor.db.AXDecorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val splash = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            addDefaultData()
            checkUser()
            finish()
        }, splash.toLong())
    }

    private fun addDefaultData() {
        val job = Job()
        val scope = CoroutineScope(job + Dispatchers.Main)
        val repository = AXDecorRepository(application)
        scope.launch {
            try {
                val data = repository.getDefaultDataFromInternet()
                repository.saveDefaultDataFromInternet(data)
                Toast.makeText(this@SplashActivity, "Datos descargados", Toast.LENGTH_SHORT).show()
                val providers = repository.getProvidersFromInternet()
                repository.saveProvidersFromInternet(providers)
                Toast.makeText(this@SplashActivity, "Proveedores descargados", Toast.LENGTH_SHORT)   .show()
                val models = repository.getModelsFromInternet()
                repository.saveModelsFromInternet(models)
                Toast.makeText(this@SplashActivity, "Modelos descargados", Toast.LENGTH_SHORT)  .show()
                val paints = repository.getPaintsFromInternet()
                repository.savePaintFromInternet(paints)
                Toast.makeText(this@SplashActivity, "Pinturas descargados", Toast.LENGTH_SHORT)  .show()
            } catch (e: Exception) {
                Toast.makeText(this@SplashActivity, e.toString(), Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun checkUser() {
        val sharePref = this.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        var Intent: Intent
        if (sharePref.contains(getString(R.string.providers_key))) {
            Intent = Intent(this, ModoDecoracionActivity::class.java)
        } else {
            Intent = Intent(this, DatosUsuarioActivity::class.java)
        }
        startActivity(Intent)
    }

}