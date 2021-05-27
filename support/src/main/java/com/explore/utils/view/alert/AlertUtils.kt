package com.explore.support.utils.view.alert

import android.app.AlertDialog
import android.content.DialogInterface

private fun noInternetAlert() {
}

fun AlertDialog.Builder.showNoInternetAlert(){

    this.
        setCancelable(false)
        .setTitle("No internet !")
        .setMessage("Please check your network connection")
        .setPositiveButton("Try again", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        .setNegativeButton("Exit", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        .show()
}