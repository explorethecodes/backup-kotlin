package com.explore.support.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.explore.support.auth.Auth
import com.explore.support.auth.BasicAuth

data class Image(
        var uri: Uri? = null,
        var url: String? = null
)

interface GlideCallbacks{
    fun onLoadFailed(errorDrawable: Drawable?)
    fun onLoadCleared(placeholder: Drawable?)
    fun onResourceReady(bitmap: Bitmap)
}

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view)
            .load(url)
            .into(view)
}

fun ImageView.loadImage(imageUri : Uri){
    Glide.with(this)
            .load(imageUri)
            .into(this)
}

fun ImageView.loadImage(imageUrl: String, auth: Auth){
    val glideAuth = LazyHeaders.Builder() // can be cached in a field and reused
        .addHeader("Authorization", BasicAuth(auth))
        .build()

    Glide.with(this)
        .load(GlideUrl(imageUrl,glideAuth))
        .into(this)
}

fun ImageView.loadImage(image: Image, callback : (Exception?) -> Unit){

    if (image.uri != null){
        callback(null)
    } else if (!image.url.isNullOrEmpty()){
        try {

            Glide.with(this)
                    .load(image.url)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            callback(e)
                            return false
                        }
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            callback(null)
                            return false
                        }
                    })
                    .into(this)
        } catch (e: Exception){
            e.printStackTrace()
            callback(e)
        }
    } else {
    }
}

fun ImageView.loadImage(imageUrl : String, callback : (Exception?) -> Unit){
    try {
        Glide.with(this)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        callback(e)
                        return false
                    }
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        callback(null)
                        return false
                    }
                })
                .into(this)
    } catch (e: Exception){
        e.printStackTrace()
    }
}

fun ImageView.loadImageInCircle(imageUrl : String){
    try {
        Glide.with(this)
                .load(imageUrl)
                .circleCrop()
                .into(this)
    } catch (e: Exception){
        e.printStackTrace()
    }
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