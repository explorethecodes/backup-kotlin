package com.explore.support.store.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.explore.support.R
import kotlinx.android.synthetic.main.item_spinner_simple.view.*

class SpinnerStringAdapter(context: Context, resource: Int, objects: MutableList<String>) : ArrayAdapter<String>(context, resource, objects) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = LayoutInflater.from(context).inflate(R.layout.item_spinner_simple,null)
        view.id_label.setText(getItem(position))
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = LayoutInflater.from(context).inflate(R.layout.item_spinner_simple,null)
        view.id_label.setText(getItem(position))
        return view
    }
}
