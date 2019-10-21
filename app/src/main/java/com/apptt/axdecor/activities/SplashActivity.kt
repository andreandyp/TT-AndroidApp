package com.apptt.axdecor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.room.Room
import com.apptt.axdecor.R
import com.apptt.axdecor.database.AXDecorDatabase
import com.apptt.axdecor.database.DAO.AXDecorDao

class SplashActivity : AppCompatActivity() {
    val splash = 500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val db = Room.databaseBuilder(this,AXDecorDatabase::class.java,"AXDecorDatabase")
            .allowMainThreadQueries()
            .build()
        val axdecordao = db.axdecorDao()
        Log.i("TESTDB",axdecordao.getAllModels().toString())
        Handler().postDelayed({
            val Intent = Intent(this, DatosUsuarioActivity::class.java)
            startActivity(Intent)
            finish()
        }, splash.toLong())
    }
}
