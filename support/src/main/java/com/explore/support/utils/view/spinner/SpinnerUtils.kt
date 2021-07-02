package com.explore.support.utils.view.spinner

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.explore.support.R

fun Spinner.initSpinner(stringArray: List<String>, callback: (String) -> Unit) {

    val adapter = SpinnerStringAdapter(
        context,
        R.layout.item_spinner_simple,
        stringArray.toMutableList()
    )
    this.adapter = adapter

    onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>,
                                    view: View, position: Int, id: Long) {
            val selected = stringArray.elementAt(position)
            callback(selected)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // write code to perform some action
        }
    }
}