package com.apptt.axdecor.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R
import com.apptt.axdecor.domain.Model
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CotizaMueblesAdapter(
    private val mcontext: Context,
    private val cantidades: HashMap<*, *>,
    private val modelos: MutableList<Model>
) :
    RecyclerView.Adapter<CotizaMueblesAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.modelo_cotiza,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return modelos.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        Log.i("TESTCOTIZA","Posicion: " + position + " " + modelos[position].name + modelos[position].price)
        Glide.with(mcontext)
            .load(modelos[position].file2D)
            .into(holder.imagen)
            .apply { RequestOptions.placeholderOf(R.drawable.loading).error(R.drawable.no)}

        holder.nombre.text = modelos[position].name
        holder.cantidad.text = "Cantidad: " + cantidades[modelos[position].idModel] as Int
        val precio =
            modelos[position].price.toFloat() * cantidades[modelos[position].idModel] as Int
        holder.precio.text = "$ $precio"
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen = itemView.findViewById<ImageView>(R.id.imModelo)
        val nombre = itemView.findViewById<TextView>(R.id.tvNombre)
        val precio = itemView.findViewById<TextView>(R.id.tvPrecio)
        val cantidad = itemView.findViewById<TextView>(R.id.tvCantidad)
    }
}