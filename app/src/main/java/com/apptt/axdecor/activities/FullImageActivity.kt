package com.apptt.axdecor.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.apptt.axdecor.R
import kotlinx.android.synthetic.main.activity_full_image.*

class FullImageActivity : AppCompatActivity() {
    lateinit var flechaAtras: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        val mBundle = intent.extras
        if (mBundle != null) {
            val file = mBundle.getString("Image")
            imageView.setImageURI(Uri.parse(file))
        }
        floatBtn.setOnClickListener {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, Uri.parse(mBundle?.getString("Image")))
                type = "image/png"
            }
            startActivity(Intent.createChooser(shareIntent, "Comparte tu decoración"))
        }
        flechaAtras = findViewById(R.id.imgMenu)
        flechaAtras.setOnClickListener {
            onBackPressed()
        }
    }
}
