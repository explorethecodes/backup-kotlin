package com.explore.journovideos

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.view.children
import com.explore.support.databinding.WidgetMultiselectCheckboxGroupBinding

class CheckBoxGroupWidget : LinearLayout {
    private var filterText: String = ""
    private var optionsSelectedListener: OptionsSelectedListener? = null
    private var data: OptionsSection = OptionsSection()
    private lateinit var binding: WidgetMultiselectCheckboxGroupBinding

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        binding = WidgetMultiselectCheckboxGroupBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    fun setData(optionsSection: OptionsSection, filterText: String) {
        this.data = optionsSection
        this.filterText = filterText
        refreshState()
    }

    fun setOptionSelectionChangedListener(optionsSelectedListener: OptionsSelectedListener) {
        this.optionsSelectedListener = optionsSelectedListener
    }

//    private fun publishUpdatedOptionValues() {
//        optionsSelectedListener?.let { optionsSelectedListener ->
//            var allSelectedOptions = mutableListOf<Option>()
//            this.data.optionsList.filter { it.checked }.let { selectedItems ->
//                allSelectedOptions.addAll(selectedItems)
//            }
//            optionsSelectedListener.OptionsUpdated(allSelectedOptions)
//        }
//    }


    private fun refreshState() {
        binding.sectionCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

        }
        if (data.sectionText == null) {
            binding.sectionCheckBox.visibility = GONE
        } else {
            binding.sectionCheckBox.visibility = VISIBLE
            binding.sectionCheckBox.text = data.sectionText
            data.updateCheckedValue()
            binding.sectionCheckBox.isChecked = data.checked
        }
        binding.checkboxContainerFL.removeAllViews()
        data.optionsList.forEach {
            if (filterText.isEmpty()) {
                binding.sectionCheckBox.isClickable=true
                var checkBox = CheckBox(context)
                checkBox.text = it.text
                checkBox.isChecked = it.checked
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

                    it.checked = isChecked
//                    publishUpdatedOptionValues()
//                    refreshState()
                    optionsSelectedListener?.let { optionsSelectedListener ->
                        if (isChecked) {
                            optionsSelectedListener.OptionsAdded(it)
                        } else {
                            optionsSelectedListener.OptionsRemoved(it)
                        }
                    }

                }
                binding.checkboxContainerFL.addView(checkBox)
            } else {
                binding.sectionCheckBox.isClickable=false
                if (it.text.contains(filterText, ignoreCase = true)) {
                    var checkBox = CheckBox(context)
                    checkBox.text = it.text
                    checkBox.isChecked = it.checked
                    checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

                        it.checked = isChecked
//                        publishUpdatedOptionValues()
                        refreshState()
                        optionsSelectedListener?.let { optionsSelectedListener ->
                            if (isChecked) {
                                optionsSelectedListener.OptionsAdded(it)
                            } else {
                                optionsSelectedListener.OptionsRemoved(it)
                            }
                        }
                    }
                    binding.checkboxContainerFL.addView(checkBox)
                }
            }
        }

        binding.sectionCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            data.setChecked(isChecked)
//            publishUpdatedOptionValues()

            binding.checkboxContainerFL.children.forEach {
                if (it is CheckBox)
                {
                    it.isChecked=isChecked
                }
            }
//            refreshState()
        }
    }


}