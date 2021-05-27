package com.explore.support.module.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.explore.support.module.auth.Auth
import com.explore.support.module.auth.BasicAuth
import java.lang.Exception

fun ImageView.loadImage(imageUrl: String, auth: Auth){
    val glideAuth = LazyHeaders.Builder() // can be cached in a field and reused
        .addHeader("Authorization", BasicAuth(auth))
        .build()

    Glide.with(this)
        .load(GlideUrl(imageUrl,glideAuth))
        .into(this)
}

fun ImageView.loadImage(imageUrl : String){
    imageUrl
    try {
        Glide.with(this)
                .load(imageUrl)
                .into(this)
    } catch (e: Exception){
        e.printStackTrace()
    }
}

fun ImageView.loadImageInCircle(imageUrl : String){
    imageUrl
    try {
        Glide.with(this)
                .load(imageUrl)
                .circleCrop()
                .into(this)
    } catch (e: Exception){
        e.printStackTrace()
    }
}

fun ImageView.loadImage(imageUri : Uri){
    Glide.with(this)
        .load(imageUri)
        .into(this)
}

fun Context.loadNotificationImages(imageUrl: String,glideCallbacks: GlideCallbacks) {
    Glide.with(this)
        .asBitmap()
        .load(imageUrl)
        .timeout(30000)
        .into(object : CustomTarget<Bitmap?>() {
            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                glideCallbacks.onLoadFailed(errorDrawable)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                glideCallbacks.onLoadCleared(placeholder)
            }

            override fun onResourceReady(
                bitmap: Bitmap,
                transition: Transition<in Bitmap?>?
            ) {
                glideCallbacks.onResourceReady(bitmap)
            }
        })
}

class NotificationImages(
    val backgroundImage: String?
) {

    data class Builder(
        var backgroundImage: String? = null
    ) {
        fun backgroundImage(backgroundImage: String) = apply { this.backgroundImage = backgroundImage }
        fun build() = NotificationImages(backgroundImage)
    }
}

interface GlideCallbacks{
    fun onLoadFailed(errorDrawable: Drawable?)
    fun onLoadCleared(placeholder: Drawable?)
    fun onResourceReady(bitmap: Bitmap)
}

@BindingAdapter("image")
fun loadImg(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}