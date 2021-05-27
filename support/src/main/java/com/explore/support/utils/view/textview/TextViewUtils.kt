package com.explore.support.utils.view.textview

import android.app.DatePickerDialog
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

fun TextView.getDate(callback: (String) -> Unit){

    var cal = Calendar.getInstance()
    val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "dd MMM yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        this.text = sdf.format(cal.time)

        val apiFormat = "dd-MMM-yyyy" // mention the format you need
        callback(SimpleDateFormat(apiFormat, Locale.US).format(cal.time))
    }

    this.setOnClickListener {

        it.requestFocus()
        var datePickerDialog = DatePickerDialog(this.context, dateSetListener, cal.get(Calendar.YEAR), cal.get(
            Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

//            var minDate = viewModel.createJobRequest.validFrom
//            if (!minDate.isNullOrEmpty()){
//                try {
//                    val format: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
//                    val date: Date = format.parse(minDate)
//
//                    datePickerDialog.datePicker.minDate = date.time
//                } catch (e : java.lang.Exception){
//                    datePickerDialog.datePicker.minDate = System.currentTimeMillis()
//                }
//            } else {
//                datePickerDialog.datePicker.minDate = System.currentTimeMillis()
//            }

        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

}