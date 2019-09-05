package com.apptt.axdecor.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apptt.axdecor.R
import com.apptt.axdecor.databinding.ActivityTutorialConceptosBinding

class TutorialConceptosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialConceptosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_tutorial_conceptos
        )
    }
}
