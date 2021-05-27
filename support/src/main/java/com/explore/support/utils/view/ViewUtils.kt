package com.explore.support.utils.view

import android.view.View
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