package com.apptt.axdecor.fragments


import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apptt.axdecor.R
import com.apptt.axdecor.domain.Paint
import kotlinx.android.synthetic.main.fragment_cotiza_pinturas.*
import kotlinx.android.synthetic.main.modelo_cotiza.*


class CotizaPinturasFragment(private val pintura: Paint?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cotiza_pinturas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bm = Bitmap.createBitmap(512,512,Bitmap.Config.ARGB_8888)
        if(pintura != null){
            bm.eraseColor(Color.parseColor(pintura?.hexCode))
            imModelo.setImageBitmap(bm)
            tvNombre.text = pintura?.name
            tvCantidad.text = "Código: ${pintura?.vendorCode}"
            tvPrecio.text = "$ ${pintura?.price}"
            tvTotal.text = "Total: ${pintura?.price}"
        }else{
            tvNombre.text = "No hay pintura"
            tvCantidad.text = ""
            tvPrecio.text = ""
            tvTotal.text = ""
        }
    }


}
