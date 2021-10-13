package com.explore.journovideos

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.children
import com.explore.support.R
import com.explore.support.databinding.WidgetMultiselectChipGroupBinding
import com.explore.support.views.hide
import com.explore.support.views.show
import kotlinx.android.synthetic.main.widget_multiselect_chip_group.view.*
import java.lang.Exception

class ChipGroupWidget : LinearLayout {

    private var expandListener: ExpandListener? = null
    private var chipRemoveListener: ChipRemoveListener? = null
    private lateinit var binding: WidgetMultiselectChipGroupBinding
    private var isExpanded = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {

        binding = WidgetMultiselectChipGroupBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        )

        var typedArray =
                context.obtainStyledAttributes(
                        attrs,
                        R.styleable.ChipsBoxLayoutWidget,
                        defStyleAttr,
                        0
                )

        var chipText = typedArray.getString(R.styleable.ChipsBoxWidget_chipText)

        id_dropdown_select.setOnClickListener {
            isExpanded = isExpanded.not()
            updateIcon(isExpanded)
            expandListener?.let {
                it.onExpandStateChanged(isExpanded)
            }
        }

        id_select_some_values.setOnClickListener {
            isExpanded = isExpanded.not()
            updateIcon(isExpanded)
            expandListener?.let {
                it.onExpandStateChanged(isExpanded)
            }
        }
    }

    private fun updateIcon(expanded: Boolean) {
        println("Icon image Change")
//        TODO("Not yet implemented")
    }

//    fun setData(chips: List<ChipsBoxWidget.Chip>) {
//        binding.idChipsContainer.removeAllViews()
//        chips.forEach {
//            var chipWidget = it.getChipWidget(context)
//            chipWidget.setonRemove(this::onRemove)
//            binding.idChipsContainer.addView(chipWidget)
//        }
//        updateCount()
//    }

    fun updateCount() {
        id_count.setText(binding.idChipsContainer.childCount.toString())

        if (binding.idChipsContainer.childCount<1){
            binding.idSelectSomeValues.show()
        } else {
            binding.idSelectSomeValues.hide()
        }

        binding.idChipsContainer
    }

    fun addChip(chip: Chip) {
        var chipWidget = chip.getChipWidget(context)
        chipWidget.tag=chip.chip_id
        chipWidget.setonRemove(this::onRemove)
        binding.idChipsContainer.addView(chipWidget)
        updateCount()
    }

    fun removeChip(chip: Chip) {
        try {
            binding.idChipsContainer.removeView(binding.idChipsContainer.children.first { (it.tag as Int) == chip.chip_id })
            updateCount()
        }catch (e:Exception)
        {
            e.printStackTrace()
        }

    }

    fun onRemove(chip: Chip) {
        chipRemoveListener?.let {
            it.onChipRemoved(chip)
        }
    }

    fun setClearButtonListener(listener: OnClickListener) {
        id_clearAll.setOnClickListener(listener)
    }


    fun setExpandCollapseEvent(expandListener: ExpandListener) {
        this.expandListener = expandListener

    }

    fun setOnRemoveButtonListener(chipRemoveListener: ChipRemoveListener) {
        this.chipRemoveListener = chipRemoveListener
    }

    interface ChipRemoveListener {
        fun onChipRemoved(chip: Chip)
    }

    interface ExpandListener {
        fun onExpandStateChanged(isExpanded: Boolean)
    }
}

fun Chip.getChipWidget(context: Context): ChipWidget {
    var chipsBoxWidget = ChipWidget(context)
    chipsBoxWidget.setData(this)

    return chipsBoxWidget
}