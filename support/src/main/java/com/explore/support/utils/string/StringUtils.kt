package com.explore.support.utils.string

import com.explore.support.store.constants.Constants.STRING.EMPTY
import com.explore.support.store.constants.Constants.STRING.NOT_AVAILABLE_IN_SHORT
import com.explore.support.store.constants.Constants.STRING.SPACE

fun String.removeStartingSlash() : String {
    return removePrefix("/")
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

fun String?.checkAvailability() : String {
    return if (this.isNullOrEmpty()) {
        NOT_AVAILABLE_IN_SHORT
    } else {
        this
    }
}

fun String?.checkAvailability(notAvailbaleWord : String) : String {
    this?.let {
        return it
    }
    return notAvailbaleWord
}

fun String.removeSquareBrackets() : String {
    return this.replace("\\[".toRegex(), "").replace("\\]".toRegex(), "").replace("\\s".toRegex(), "")
}