package com.explore.support.views.rating

import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.widget.RatingBar

fun RatingBar.setColor(color: Int){

//    val progress: Drawable = progressDrawable
//    progressDrawable.setTint(color)

    val stars = progressDrawable as LayerDrawable
    stars.getDrawable(1).setColorFilter(resources.getColor(color), PorterDuff.Mode.SRC_ATOP)
    stars.getDrawable(2).setColorFilter(resources.getColor(color), PorterDuff.Mode.SRC_ATOP)
}