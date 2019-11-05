package com.apptt.axdecor.activities

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.apptt.axdecor.R
import com.example.galeriarecyclerview.MyGalleryAdapter
import kotlinx.android.synthetic.main.activity_galeria.*
import java.io.File

class GaleriaActivity : AppCompatActivity() {
    lateinit var flechaAtras: ImageView
    var lista: ArrayList<File>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityCompat.requestPermissions(
            this as Activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria)
        val carpeta =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "AXDecor" + File.separator)
        lista = imageReader(carpeta)
        var mGridLayoutManager = GridLayoutManager(this, 3)
        mrecyclerView.layoutManager = mGridLayoutManager
        mrecyclerView.adapter = MyGalleryAdapter(this, lista!!)
        flechaAtras = findViewById(R.id.imgMenu)
        flechaAtras.setOnClickListener {
            onBackPressed()
        }
    }

    private fun imageReader(externalStorageDirectory: File): ArrayList<File> {
        val b = ArrayList<File>()
        val files = externalStorageDirectory.listFiles()
        for (i in files.indices) {
            if (files[i].isDirectory()) {
                b.addAll(imageReader(files[i]))
            } else {
                if (files[i].name.endsWith(".png")) {
                    b.add(files[i])
                }
            }
        }
        return b
    }
}
