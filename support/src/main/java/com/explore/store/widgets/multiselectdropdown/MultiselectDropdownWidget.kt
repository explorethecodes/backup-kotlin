package com.explore.support

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.explore.support.databinding.WidgetMultiselectDropdownBinding
import kotlinx.android.synthetic.main.widget_multiselect_dropdown.view.*

class MultiselectDropdownWidget : LinearLayout {

    private var idHeader: Int = 0

    private var optionsSelectedListener: OptionsSelectedListener? = null
    private lateinit var bindingMultiselectDropdownWidget: WidgetMultiselectDropdownBinding

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {

        bindingMultiselectDropdownWidget = WidgetMultiselectDropdownBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        )

        var typedArray =
                context.obtainStyledAttributes(
                        attrs,
                        R.styleable.ChipsBoxWidget,
                        defStyleAttr,
                        0
                )

        var chipText = typedArray.getString(R.styleable.ChipsBoxWidget_chipText)

        bindingMultiselectDropdownWidget.idSearchableGroup.setOptionSelectionChangedListener(object : OptionsSelectedListener {
            override fun OptionsAdded(newOptionsSelected: Option) {
                bindingMultiselectDropdownWidget.idChipBoxLayout.addChip(Chip(newOptionsSelected.id, newOptionsSelected.text))
                optionsSelectedListener?.OptionsAdded(newOptionsSelected)
            }

            override fun OptionsRemoved(newOptionRemoved: Option) {
                bindingMultiselectDropdownWidget.idChipBoxLayout.removeChip(Chip(newOptionRemoved.id, newOptionRemoved.text))
                optionsSelectedListener?.OptionsRemoved(newOptionRemoved)

            }
        })
        bindingMultiselectDropdownWidget.idChipBoxLayout.setClearButtonListener(object : OnClickListener {
            override fun onClick(p0: View?) {
                bindingMultiselectDropdownWidget.idSearchableGroup.clearAll()
            }

        })

        bindingMultiselectDropdownWidget.idChipBoxLayout.setOnRemoveButtonListener(object : ChipGroupWidget.ChipRemoveListener {
            override fun onChipRemoved(chip: Chip) {
                bindingMultiselectDropdownWidget.idSearchableGroup.OptionUnchecked(chip.chip_id)
            }
        }
        )

        bindingMultiselectDropdownWidget.idChipBoxLayout.setExpandCollapseEvent(object : ChipGroupWidget.ExpandListener {
            override fun onExpandStateChanged(isExpanded: Boolean) {
                if (isExpanded) {
                    bindingMultiselectDropdownWidget.idSearchableGroup.expand()
                } else {
                    bindingMultiselectDropdownWidget.idSearchableGroup.collapse()
                }
            }

        })


    }

    fun expand()
    {
        bindingMultiselectDropdownWidget.idSearchableGroup.expand()
    }

    fun setData(optionsSections: List<OptionsSection>) {
        bindingMultiselectDropdownWidget.idSearchableGroup.setData(optionsSections)
    }

    fun setOptionSelectionChangedListener(optionsSelectedListener: OptionsSelectedListener) {
        this.optionsSelectedListener = optionsSelectedListener
    }

    fun setHeader(header: String, id : Int) {
        id_header.visibility = VISIBLE
        id_header.setText(header)
        this.idHeader = id
    }

    fun getHeaderId(): Int {
        return idHeader

    }

    fun getHeader():String
    {
        return id_header.text.toString()
    }

    fun getSelectedValues(): MutableList<Option> {
        return bindingMultiselectDropdownWidget.idSearchableGroup.getSelectedOptions()
    }
}