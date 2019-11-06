package com.apptt.axdecor.utilities

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.apptt.axdecor.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("modeloUrl")
fun bindImagenModelo(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions().placeholder(R.drawable.loading).error(R.drawable.no))
            .apply(RequestOptions.circleCropTransform().timeout(5_000))
            .into(imgView)
    }
}