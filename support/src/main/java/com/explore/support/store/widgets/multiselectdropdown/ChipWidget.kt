package com.explore.journovideos

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.explore.support.R
import com.explore.support.databinding.WidgetMultiselectChipBinding
import kotlinx.android.synthetic.main.widget_multiselect_chip.view.*
import kotlin.reflect.KFunction1

class ChipWidget : LinearLayout {

    private var removeRefFun: KFunction1<Chip, Unit>? = null
    private var chip: Chip? = null

    private lateinit var bindingWidgetChipBox: WidgetMultiselectChipBinding

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {

        bindingWidgetChipBox = WidgetMultiselectChipBinding.inflate(
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

        id_chip_close.setOnClickListener {
            removeRefFun?.let {
                this.chip?.let { it1 -> it.invoke(it1) }
            }
        }
    }

    fun setData(chip: Chip) {
        this.chip = chip
        bindingWidgetChipBox.idChipText.setText(chip.chip_text)
    }

    fun setonRemove(kFunction1: KFunction1<Chip, Unit>) {
        this.removeRefFun = kFunction1
    }

}

data class Chip(
        val chip_id: Int,
        val chip_text: String
)