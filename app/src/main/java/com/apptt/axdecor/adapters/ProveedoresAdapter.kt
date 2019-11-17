package com.apptt.axdecor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R
import com.apptt.axdecor.domain.CategoryProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ProveedoresAdapter(
    private val mList: CategoryProvider,
    private val callback: (Int, Int, Boolean) -> Unit,
    private val context: Context
) :
    RecyclerView.Adapter<ProveedoresAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.proveedor_radio_button,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mList.providers.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        Glide.with(context)
            .load(mList.logos[position])
            .apply(
                RequestOptions().placeholder(R.drawable.loading).error(R.drawable.no).timeout(
                    5_000
                )
            )
            .into(holder.logo)
        holder.check.text = mList.providers[position]
        holder.check.setOnCheckedChangeListener { _, isChecked ->
            callback(mList.idProviders[position], mList.idCategory, isChecked)
        }
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val check: CheckBox = itemView.findViewById(R.id.rbProv)
        val logo: ImageView = itemView.findViewById(R.id.logo_proveedor)
        //val nombreProveedor: TextView = itemView.findViewById(R.id.nombre_proveedor)
    }
}