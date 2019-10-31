package com.apptt.axdecor.dialogs


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.apptt.axdecor.R
import com.google.android.material.textview.MaterialTextView


class SugerenciaPinturaDialog(color: String) : DialogFragment() {
    private val colorPaleta = color
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
            val paleta = view.findViewById<ImageView>(R.id.imPaleta)
            val texto1 = view.findViewById<MaterialTextView>(R.id.texto1)
            val texto2 = view.findViewById<MaterialTextView>(R.id.tvTexto)
            when (colorPaleta) {
                "Amarillo" -> {
                    paleta.setImageResource(R.drawable.amarillos)
                    texto1.setText("Te recomendamos el color: amarillo")
                    texto2.setText(R.string.sugerenciaAmarillo)
                }
                "Azul" -> {
                    paleta.setImageResource(R.drawable.azules)
                    texto1.setText("Te recomendamos el color: azul")
                    texto2.setText(R.string.sugerenciaAzul)
                }
                "Rojo" -> {
                    paleta.setImageResource(R.drawable.rojos)
                    texto1.setText("Te recomendamos el color: rojo")
                    texto2.setText(R.string.sugerenciaRojo)
                }
                "Verde" -> {
                    paleta.setImageResource(R.drawable.verdes)
                    texto1.setText("Te recomendamos el color: verde")
                    texto2.setText(R.string.sugerenciaVerde)
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return act
    }


}
