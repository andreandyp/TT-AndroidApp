package com.apptt.axdecor.utilities

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apptt.axdecor.adapters.CatalogoAdapter
import com.apptt.axdecor.domain.Model
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("catalogoList")
fun bindCatalogo(recyclerView: RecyclerView, catalogo: List<Model>) {
    val adapter = recyclerView.adapter as CatalogoAdapter
    adapter.submitList(catalogo)
}

@BindingAdapter("modeloUrl")
fun bindImagenModelo(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions.circleCropTransform())
            .into(imgView)
    }
}