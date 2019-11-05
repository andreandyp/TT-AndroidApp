package com.apptt.axdecor.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.apptt.axdecor.R
import com.apptt.axdecor.activities.CotizacionActivity
import com.apptt.axdecor.activities.GaleriaActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CotizaDialog : DialogFragment() {
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
                        1 -> {//Activity de cotización, se manda Lista de elementos añadidos
                            val mIntent = Intent(activity, CotizacionActivity::class.java)
                            startActivity(mIntent)
                        }
                    }
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}