package com.apptt.axdecor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R
import com.apptt.axdecor.domain.Store
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ContactoPinturaAdapter(
    private val mcontext: Context,
    private val tiendas: List<Store>
) :
    RecyclerView.Adapter<ContactoPinturaAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.contacto_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return tiendas.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        Glide.with(mcontext)
            .load(tiendas[position].logo)
            .apply(RequestOptions().placeholder(R.drawable.loading).error(R.drawable.no))
            .apply(RequestOptions.centerCropTransform().timeout(5_000))
            .into(holder.logo)
        holder.direccion.text = tiendas[position].address
        holder.phone.text = tiendas[position].phone
        holder.email.text = tiendas[position].email
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo = itemView.findViewById<ImageView>(R.id.imProveedor)
        val direccion = itemView.findViewById<TextView>(R.id.tvNombre)
        val phone = itemView.findViewById<TextView>(R.id.tvPhone)
        val email = itemView.findViewById<TextView>(R.id.tvEmail)
    }
}