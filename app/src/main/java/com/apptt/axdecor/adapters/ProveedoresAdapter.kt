package com.apptt.axdecor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R

class ProveedoresAdapter(context: Context, list: List<String>) :
    RecyclerView.Adapter<ProveedoresAdapter.PlaceViewHolder>() {
    val mContext = context
    val mList = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return ProveedoresAdapter.PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.proveedor_radio_button,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.radioBoton.setText(mList.get(position))
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioBoton = itemView.findViewById<RadioButton>(R.id.rbProv)
    }
}