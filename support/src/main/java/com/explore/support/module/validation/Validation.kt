package com.explore.support.module.validation

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

fun isValidMobile(phone: CharSequence?): Boolean {
    return Patterns.PHONE.matcher(phone).matches()
}

fun isValidEmail(target: CharSequence?): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
}

/*

^                 # start-of-string
(?=.*[0-9])       # a digit must occur at least once
(?=.*[a-z])       # a lower case letter must occur at least once
(?=.*[A-Z])       # an upper case letter must occur at least once
(?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
(?=\\S+$)          # no whitespace allowed in the entire string
.{4,}             # anything, at least six places though
$                 # end-of-string

*/


fun String.isValidPassword(): Boolean {
    var isValidPassword: Boolean
    val pattern: Pattern
    val matcher: Matcher
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&]).{3,}"
    pattern = Pattern.compile(PASSWORD_PATTERN)
    matcher = pattern.matcher(this)
    isValidPassword = matcher.matches()
    return isValidPassword
}

fun String?._isValidPassword() : Boolean {
    this?.let {
        val PASSWORD_PATTERN = "^(?=.*[A-Z])$"
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{3,}$"
        val passwordMatcher = Regex(PASSWORD_PATTERN)

        return passwordMatcher.find(it) != null
    } ?: return false
}