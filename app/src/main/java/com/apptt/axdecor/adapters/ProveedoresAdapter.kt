package com.apptt.axdecor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R
import com.apptt.axdecor.domain.CategoryProvider

class ProveedoresAdapter(private val mList: CategoryProvider, private val callback: (Int, Int, Boolean) -> Unit) :
    RecyclerView.Adapter<ProveedoresAdapter.PlaceViewHolder>() {

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
        return mList.providers.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.check.setText(mList.providers[position])
        holder.check.setOnCheckedChangeListener { buttonView, isChecked ->
            callback(mList.idProviders[position], mList.idCategory, isChecked)
        }
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val check = itemView.findViewById<CheckBox>(R.id.rbProv)
    }
}