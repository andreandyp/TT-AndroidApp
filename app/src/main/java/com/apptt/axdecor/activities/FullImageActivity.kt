package com.apptt.axdecor.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import com.apptt.axdecor.R
import kotlinx.android.synthetic.main.activity_full_image.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FullImageActivity : AppCompatActivity() {
    lateinit var flechaAtras: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        val mBundle = intent.extras
        botones.visibility = View.GONE
        btnCancela.visibility = View.GONE
        btnGuardar.visibility = View.GONE
        muestraCarga(0)
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
        floatLuz.setOnClickListener {
            btnCancela.visibility = View.VISIBLE
            btnGuardar.visibility = View.VISIBLE
            botones.visibility = View.VISIBLE
        }
        btnCancela.setOnClickListener {
            btnCancela.visibility = View.INVISIBLE
            btnGuardar.visibility = View.INVISIBLE
            botones.visibility = View.INVISIBLE
            imageView.clearColorFilter()
        }
        btnCalida.setOnClickListener {
            aplicaFiltro(imageView, 255, 174, 84) // 2700K Luz calida
        }
        btnNeutra.setOnClickListener {
            aplicaFiltro(imageView, 255, 215, 177) // 4000K Luz Neutra
        }
        btnFria.setOnClickListener {
            aplicaFiltro(imageView, 195, 209, 255) // 12000K Luz Fria
        }
        btnGuardar.setOnClickListener {
            muestraCarga(1)
            val archivo = generateFilename()
            val bitmap = imageView.drawToBitmap()
            try {
                saveBitmapToDisk(bitmap, archivo)
                Toast.makeText(this, "Imagen Guardada Exitosamente!", Toast.LENGTH_SHORT).show()
                val mIntent = Intent(this, GaleriaActivity::class.java)
                startActivity(mIntent)
                muestraCarga(0)
            } catch (e: IOException) {
                val toast = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
                Log.i("ERRORPHOTO", e.printStackTrace().toString())
                toast.show()
            }
        }
        flechaAtras = findViewById(R.id.imgMenu)
        flechaAtras.setOnClickListener {
            onBackPressed()
        }
    }

    private fun aplicaFiltro(imageView: ImageView, RED: Int, GREEN: Int, BLUE: Int) {
        var colorMatrix = ColorMatrix()
        colorMatrix.set(
            floatArrayOf(
                RED / 255.0f, 0f, 0f, 0f, 0f,
                0f, GREEN / 255.0f, 0f, 0f, 0f,
                0f, 0f, BLUE / 255.0f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        imageView.colorFilter = ColorMatrixColorFilter(colorMatrix)
    }

    @Throws(IOException::class)
    private fun saveBitmapToDisk(bitmap: Bitmap, filename: String) {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                this,
                "No nos diste permiso para guardar la imagen :(",
                Toast.LENGTH_LONG
            ).show()
        }
        val out = File(filename)
        if (!out.parentFile.exists()) {
            out.parentFile.mkdirs()
        }
        out.createNewFile()
        try {
            val outputStream = FileOutputStream(out)
            val outputData = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData)
            outputData.writeTo(outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (ex: IOException) {
            Log.i("ERRORPHOTO", ex.toString())
            throw IOException("Fallo al guardar ", ex)
        }
    }

    private fun generateFilename(): String {
        val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "AXDecor/" + date + "_screenshot.png"
    }

    private fun muestraCarga(flag: Int) {
        when (flag) {
            1 -> {
                cargando.visibility = View.VISIBLE
                tvCargando.visibility = View.VISIBLE
            }
            0 -> {
                cargando.visibility = View.GONE
                tvCargando.visibility = View.GONE
            }
        }
    }

}
