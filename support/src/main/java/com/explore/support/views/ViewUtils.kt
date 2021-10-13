package com.explore.support.views

import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import com.google.android.material.snackbar.Snackbar


fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun View.enable(){
    isEnabled = true
}

fun View.disable(){
    isEnabled = false
}

fun View.snackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.setMargins(left: Int?, top: Int?, right: Int?, bottom: Int?) {
    if (layoutParams is MarginLayoutParams) {
        val p = layoutParams as MarginLayoutParams

        var leftMargin = p.leftMargin
        var topMargin = p.topMargin
        var rightMargin = p.rightMargin
        var bottomMargin = p.bottomMargin

        left?.let {
            leftMargin = it
        }
        top?.let {
            topMargin = it
        }
        right?.let {
            rightMargin = it
        }
        bottom?.let {
            bottomMargin = it
        }

        p.setMargins(leftMargin,topMargin,rightMargin,bottomMargin)

        requestLayout()
    }
}

fun View.resetMargin(){
    setMargins(null,null,null,null)
}