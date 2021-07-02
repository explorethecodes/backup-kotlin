package com.explore.support.utils.context

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.explore.support.store.constants.Constants.STRING.PRESS_BACK_AGAIN_TO_EXIT
import com.explore.support.store.constants.Constants.TIME_DURATION.TOAST_DELAY_TIME
import java.io.IOException

fun Context.parseJson(fileName: String, type : Any){
    val gson = Gson()
    val jsonString = getJsonDataFromAsset(fileName)

    val listPersonType = object : TypeToken<Any>() {}.type
    var charts: Any = gson.fromJson(jsonString, listPersonType)
}

fun Context.getJsonDataFromAsset(fileName: String): String? {
    val jsonString: String
    try {
        jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

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

fun Context.lifecycleOwner(): LifecycleOwner? {
    var curContext = this
    var maxDepth = 20
    while (maxDepth-- > 0 && curContext !is LifecycleOwner) {
        curContext = (curContext as ContextWrapper).baseContext
    }
    return if (curContext is LifecycleOwner) {
        curContext as LifecycleOwner
    } else {
        null
    }
}