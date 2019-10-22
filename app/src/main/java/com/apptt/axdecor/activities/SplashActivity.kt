package com.apptt.axdecor.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.apptt.axdecor.R
import com.apptt.axdecor.database.AXDecorDatabase
import com.apptt.axdecor.database.AXDecorRepository
import com.apptt.axdecor.database.Entities.Model
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    private val splash = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            checkUser()
            finish()
        }, splash.toLong())
    }

    private fun checkUser(){
        val sharePref = this.getSharedPreferences(getString(R.string.preference_file_key_datos),Context.MODE_PRIVATE)?: return
        var Intent:Intent
        if (sharePref.contains(getString(R.string.user_Name))){
            Intent = Intent(this, TutorialConceptosActivity::class.java)
        }else{
            Intent = Intent(this, DatosUsuarioActivity::class.java)
        }
        startActivity(Intent)
    }

}