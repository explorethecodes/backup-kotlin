package com.explore.support.utils.date

import android.app.DatePickerDialog
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

fun today(){

}

fun getDifferenceInDays(startDate: Date, endDate: Date): Long {

    var different: Long = endDate.getTime() - startDate.getTime()

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = different / daysInMilli

    return elapsedDays
}

fun printDifference(startDate: Date, endDate: Date) {
    //milliseconds
    var different: Long = endDate.getTime() - startDate.getTime()
    println("startDate : $startDate")
    println("endDate : $endDate")
    println("different : $different")

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = different / daysInMilli

    different = different % daysInMilli
    val elapsedHours = different / hoursInMilli
    different = different % hoursInMilli
    val elapsedMinutes = different / minutesInMilli
    different = different % minutesInMilli
    val elapsedSeconds = different / secondsInMilli
    System.out.printf(
            "%d days, %d hours, %d minutes, %d seconds%n",
            elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)
}