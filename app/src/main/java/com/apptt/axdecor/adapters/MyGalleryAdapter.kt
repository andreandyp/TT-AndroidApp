package com.example.galeriarecyclerview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.R
import com.apptt.axdecor.activities.FullImageActivity
import java.io.File


class MyGalleryAdapter(context: Context, list: ArrayList<File>) :
    RecyclerView.Adapter<MyGalleryAdapter.PlaceViewHolder>() {
    val mContext = context
    val mList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.foto_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.imagen.setImageURI(Uri.parse(mList.get(position).toString()))
        holder.imagen.setOnClickListener {
            val mIntent = Intent(mContext, FullImageActivity::class.java)
            mIntent.putExtra("Image", mList[holder.adapterPosition].toString())
            mContext.startActivity(mIntent)
        }
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen = itemView.findViewById<ImageView>(R.id.ivPlace)
    }
}