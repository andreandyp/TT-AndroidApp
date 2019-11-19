package com.apptt.axdecor.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.apptt.axdecor.R
import com.apptt.axdecor.activities.CotizacionesActivity
import com.apptt.axdecor.activities.GaleriaActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class CotizaDialog(private val modelosCotizar: HashMap<Int, Int>) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder.setTitle("¿Qué hacemos ahora?")
                .setIcon(R.drawable.ic_logo_fondo_02)
                .setItems(R.array.opcionesCotiza, DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        0 -> {
                            val mIntent = Intent(activity, GaleriaActivity::class.java)
                            startActivity(mIntent)
                        }
                        1 -> {
                            val mIntent = Intent(activity, CotizacionesActivity::class.java)
                            mIntent.putExtra("modelos",modelosCotizar)
                            startActivity(mIntent)
                        }
                    }
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}