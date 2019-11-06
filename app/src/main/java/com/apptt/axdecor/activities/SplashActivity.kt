package com.apptt.axdecor.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.apptt.axdecor.R
import com.apptt.axdecor.db.AXDecorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SplashActivity : AppCompatActivity() {
    private val splash = 0

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
        /*scope.launch {
            try {
                val data = repository.getDefaultDataFromInternet()
                repository.saveDefaultDataFromInternet(data)
                val providers = repository.getProvidersFromInternet()
                repository.saveProvidersFromInternet(providers)
                val models = repository.getModelsFromInternet()
                repository.saveModelsFromInternet(models)
            }
            catch (e: Exception) {
                Toast.makeText(this@SplashActivity, e.toString(), Toast.LENGTH_LONG).show()
            }

        }*/
    }

    private fun checkUser() {
        val sharePref = this.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        var Intent: Intent
        if (sharePref.contains(getString(R.string.user_Name))) {
            Intent = Intent(this, TutorialConceptosActivity::class.java)
        } else {
            Intent = Intent(this, DatosUsuarioActivity::class.java)
        }
        startActivity(Intent)
    }

}