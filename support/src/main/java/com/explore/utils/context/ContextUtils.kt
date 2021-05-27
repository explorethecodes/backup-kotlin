package com.explore.support.utils.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.explore.support.store.constants.Constants
import com.explore.support.store.constants.Constants.STRING.PRESS_BACK_AGAIN_TO_EXIT
import com.explore.support.store.constants.Constants.TIME_DURATION.TOAST_DELAY_TIME

fun Context.navigateTo(destination: Class<Activity>) {
    Intent(this, destination).also {
//        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  and Intent.FLAG_ACTIVITY_NO_ANIMATION
        it.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(it)
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
}

private var exit = false
fun Context.exitFromTheApp() {
    if (exit) {
        val activity : Activity = this as Activity
        activity.finish()
    } else {
        this.toast(PRESS_BACK_AGAIN_TO_EXIT)
        exit = true
        Handler().postDelayed(Runnable { exit = false}, TOAST_DELAY_TIME)
    }
}