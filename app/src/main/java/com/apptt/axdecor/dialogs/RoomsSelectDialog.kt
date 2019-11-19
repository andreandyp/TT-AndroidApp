package com.apptt.axdecor.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.apptt.axdecor.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RoomsSelectDialog(clase:Class<Any>) : DialogFragment() {
    private val claseX = clase
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder.setTitle("¿Qué habitación desearías decorar?")
                .setIcon(R.drawable.ic_logo_fondo_02)
                .setItems(R.array.Habitaciones, DialogInterface.OnClickListener { _ , which ->
                    when (which) {
                        0 -> {
                            updateRoom("Baño", 1)
                        }
                        1 -> {
                            updateRoom("Cocina", 5)
                        }
                        2 -> {
                            updateRoom("Comedor", 3)
                        }
                        3 -> {
                            updateRoom("Recamara", 2)
                        }
                        4 -> {
                            updateRoom("Sala", 4)
                        }
                    }
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun updateRoom(Room: String, idType: Int) {
        val sharePref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        with(sharePref.edit()) {
            putString(getString(R.string.room_key), Room)
            putInt(getString(R.string.id_room_key), idType)
            commit()
        }
        activity?.finish()
        val inten = Intent(activity, claseX)
        startActivity(inten)
    }
}
