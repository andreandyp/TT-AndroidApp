package com.apptt.axdecor.Dialogs


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.apptt.axdecor.R


class SugerenciaPinturaDialog(color: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val act = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.pinturas_dialog, null)
            builder.setView(view)
                .setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            view.findViewById<ImageView>(R.id.imPaleta)?.setImageResource(R.drawable.rojos)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return act
    }


}
