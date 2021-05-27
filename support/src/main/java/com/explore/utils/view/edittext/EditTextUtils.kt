package com.explore.support.utils.view.edittext

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.explore.support.utils.string.countWords

fun EditText.onPressEnterKey(callback: () -> Unit){
    setOnKeyListener(object : View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
            if (event.action === KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                        callback()
                        return true
                    }
                    else -> {
                    }
                }
            }
            return false
        }
    })
}

fun EditText.formatPhoneNumber(countryCode : String){

    addTextChangedListener(PhoneNumberFormattingTextWatcher(countryCode))

    inputType = InputType.TYPE_CLASS_PHONE
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
}

fun EditText.formatPhoneNumber(){
    val maxNumberOfDigits = 10
    val breakPoints = mutableListOf(3,5,7)
    val breaker = " - "

    val practicalBreakPoints = mutableListOf<Int>()
    breakPoints.forEachIndexed{ index, breakPoint -> practicalBreakPoints.add(breakPoint + index * breaker.length) }

    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val numberOfDigits = text.toString().length
            practicalBreakPoints.forEach{ practicalBreakPoint ->
                when (numberOfDigits) {
                    practicalBreakPoint -> {
                        append(breaker)
                    }
                    practicalBreakPoint+(breaker.length-1) -> {
                        val lastCharacters = s.substring(0,practicalBreakPoint-1)
                        text.clear()
                        append(lastCharacters)
                    }
                }
            }
        }

        override fun afterTextChanged(s: Editable) {}
    })

    inputType = InputType.TYPE_CLASS_PHONE
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxNumberOfDigits + breakPoints.size * breaker.length))
}

fun EditText.countWords(callback: (String) -> Unit){
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val numberOfWords = s!!.countWords()
            callback(numberOfWords.toString())
//            idDescriptionLimit.text = "$numberOfWords / 1000 words"
        }
    }

    this.addTextChangedListener (textWatcher)
}