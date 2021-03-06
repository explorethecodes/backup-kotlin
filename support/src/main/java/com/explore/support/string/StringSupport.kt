package com.explore.support.string

import com.explore.support.constants.ConstantSupport.STRING.EMPTY
import com.explore.support.constants.ConstantSupport.STRING.NOT_AVAILABLE_IN_SHORT
import com.explore.support.constants.ConstantSupport.STRING.SPACE

fun String.removeStartingSlash() : String {
    return removePrefix("/")
}

fun String.removeSquareBrackets() : String {
    return this.replace("\\[".toRegex(), "").replace("\\]".toRegex(), "").replace("\\s".toRegex(), "")
}

fun String?.checkAvailability() : String {
    return if (this.isNullOrEmpty()) {
        NOT_AVAILABLE_IN_SHORT
    } else {
        this
    }
}

fun String?.checkAvailability(notAvailableWord : String) : String {
    this?.let {
        return it
    }
    return notAvailableWord
}

fun CharSequence.countWords() : Int {
    val sentence = this.toString().trim()
//    sentence.split("\\s+").first().length

    //Split String by Space
    val strArray = sentence.split(SPACE.toRegex()).toTypedArray() // Spilt String by Space
    var count = 0

    //iterate String array
    for (s in strArray) {
        if (s != EMPTY) {
            count++
        }
    }

    return count
}