package com.apptt.axdecor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R
import com.apptt.axdecor.domain.CategoryProvider

class ProveedoresAdapter(context: Context, list: CategoryProvider) :
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
        return mList.providers.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.check.setText(mList.providers[position])
        holder.check.setOnCheckedChangeListener { buttonView, isChecked ->

        }
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val check = itemView.findViewById<CheckBox>(R.id.rbProv)
    }
}