package com.explore.support.utils.date

import android.os.CountDownTimer
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_JOURNO = "yyyy-MM-dd HH:mm:ss"
const val DATE_FORMAT_NOW = "dd/M/yyyy hh:mm:ss"
const val DATE_FORMAT_TODAY = "dd"
const val DATE_FORMAT_MONTH = "MMM"
const val DATE_FORMAT_YEAR = "yyyy"

enum class DateFormat{
    Now,
    Day,
    Month,
    Year
}

fun FragmentActivity.counterTimer(expireOn : String, lifecycle: Lifecycle, callback: (Boolean,String, String, String, String) -> Unit){

    val currentDateTime = now(DATE_FORMAT_JOURNO).toDate(DATE_FORMAT_JOURNO)
    val endDateTime = expireOn.toDate(DATE_FORMAT_JOURNO)

    if (currentDateTime!==null && endDateTime!=null){

        if (endDateTime.after(currentDateTime)){
            val dateDifference = getDateDifference(currentDateTime,endDateTime)

            val timer = object: CountDownTimer(dateDifference, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var diff = millisUntilFinished
                    val secondsInMilli: Long = 1000
                    val minutesInMilli = secondsInMilli * 60
                    val hoursInMilli = minutesInMilli * 60
                    val daysInMilli = hoursInMilli * 24

                    val elapsedDays = diff / daysInMilli
                    diff %= daysInMilli

                    val elapsedHours = diff / hoursInMilli
                    diff %= hoursInMilli

                    val elapsedMinutes = diff / minutesInMilli
                    diff %= minutesInMilli

                    val elapsedSeconds = diff / secondsInMilli

                    var days = elapsedDays.toString()
                    var hours = elapsedHours.toString()
                    var minutes = elapsedMinutes.toString()
                    var seconds = elapsedSeconds.toString()

                    if (elapsedDays<10){
                        days = "0$days"
                    }
                    if (elapsedHours<10){
                        hours = "0$hours"
                    }
                    if (elapsedMinutes<10){
                        minutes = "0$minutes"
                    }
                    if (elapsedSeconds<10){
                        seconds = "0$seconds"
                    }
                    callback(false,days,hours,minutes,seconds)
                }

                override fun onFinish() {

                }
            }
            timer.start()

            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart(){
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                fun onCreate(){
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                fun onPause(){
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                fun onResume(){
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
                fun onStop(){
                    timer.onFinish()
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy(){
                    timer.onFinish()
                }
            })
        } else {
            callback(true,"","","","")
        }
    }
}

fun String.toDate(dateFormat : String) : Date? {
    var date : Date? =null
    try {
        date = SimpleDateFormat(dateFormat).parse(this)
    } catch (e:Exception){
        date = null
    }
    return date
}

fun getDateDifference(startDate: Date,endDate: Date): Long {
    return endDate.getTime() - startDate.getTime()
}
fun FragmentActivity.now() : String{
    val sdf = SimpleDateFormat(DATE_FORMAT_TODAY)
    val currentDate = sdf.format(Date())
    return currentDate
}

fun FragmentActivity.now(dateFormat: String) : String{
    val sdf = SimpleDateFormat(dateFormat)
    val currentDate = sdf.format(Date())
    return currentDate
}

fun now(dateFormat: String) : String{
    val sdf = SimpleDateFormat(dateFormat)
    val currentDate = sdf.format(Date())
    return currentDate
}

fun FragmentActivity.now(dateFormat: DateFormat) : String{
    var dateFromatString = DATE_FORMAT_TODAY
    when(dateFormat){
        DateFormat.Now -> dateFromatString = DATE_FORMAT_NOW
        DateFormat.Day -> dateFromatString = DATE_FORMAT_TODAY
        DateFormat.Month -> dateFromatString = DATE_FORMAT_MONTH
        DateFormat.Year -> dateFromatString = DATE_FORMAT_YEAR
    }
    val sdf = SimpleDateFormat(dateFromatString)
    val currentDate = sdf.format(Date())
    return currentDate
}

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