package com.explore.support.utils.number

fun percentage(available: Float, total : Float) : Float {
    return ((available/total) * 100)
}