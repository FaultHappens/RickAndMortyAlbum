package com.example.rickmortyalbum.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImageFromUrl(url: String){
    context?.let {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}