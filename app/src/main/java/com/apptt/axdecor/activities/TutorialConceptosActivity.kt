package com.apptt.axdecor.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apptt.axdecor.R
import com.apptt.axdecor.databinding.ActivityTutorialConceptosBinding

class TutorialConceptosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialConceptosBinding
    lateinit var flechaAtras: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_tutorial_conceptos
        )
        flechaAtras = findViewById(R.id.imgMenu)
        flechaAtras.setOnClickListener {
            onBackPressed()
        }
    }
}
