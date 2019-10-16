package com.apptt.axdecor.Dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.apptt.axdecor.R
import com.apptt.axdecor.activities.ARCrearActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RoomsSelectDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder.setTitle("¿Qué habitación desearías decorar?")
                .setIcon(R.drawable.ic_logo_fondo_02)
                .setItems(R.array.Habitaciones, DialogInterface.OnClickListener { dialog, which ->
                    when(which){
                        0 -> {updateRoom("Baño")}       //Baño
                        1 -> {updateRoom("Cocina")}     //Cocina
                        2 -> {updateRoom("Comedor")}    //Comedor
                        3 -> {updateRoom("Sala")}       //Sala
                        4 -> {updateRoom("Recamara")}   //Recamara
                    }
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun updateRoom(Room:String){
        val sharePref = activity?.getSharedPreferences(getString(R.string.preference_file_key_datos),Context.MODE_PRIVATE)?: return
        with(sharePref.edit()){
            putString(getString(R.string.room_key),Room)
            commit()
        }
        val inten = Intent(activity,ARCrearActivity::class.java)
        startActivity(inten)
    }
}
