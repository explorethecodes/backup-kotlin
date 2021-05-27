package com.explore.support

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import com.explore.support.databinding.WidgetMultiselectSearchableGroupBinding

class SearchableGroupMultiSelector : LinearLayout {

    private var filterText: String = ""
    private var binding: WidgetMultiselectSearchableGroupBinding

    private var data = mutableListOf<OptionsSection>()

    private var optionsSelectedListener: OptionsSelectedListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {

        println("binding = WidgetSearchableGroupMultiSelectorBinding.inflate")
        binding = WidgetMultiselectSearchableGroupBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        )

        var typedArray =
                context.obtainStyledAttributes(
                        attrs,
                        R.styleable.SearchableGroupMultiSelector,
                        defStyleAttr,
                        0
                )

        var searchText = typedArray.getString(R.styleable.SearchableGroupMultiSelector_searchText)
        binding.searchTextET.hint = searchText

        binding.searchTextET.addTextChangedListener {
            filterText = it.toString()
            refreshState()
        }

        refreshState()
    }


    fun expand() {
        binding.expandableLayout.expand(true)
    }

    fun collapse() {
        binding.expandableLayout.collapse(true)
    }

    fun clearAll() {
        this.data.forEach {
            it.optionsList.forEach {
                if (it.checked) {
                    it.checked = false
                    removed(it)
                }
            }
        }
    }

    fun setData(data: List<OptionsSection>) {
        this.data = data.toMutableList()

        refreshState()
        //publishUpdatedOptions()
    }

    private fun removed(option: Option) {
        this.optionsSelectedListener?.let { optionsSelectedListener ->
            optionsSelectedListener.OptionsRemoved(option)
            refreshState()
        }
    }

    private fun added(option: Option) {
        this.optionsSelectedListener?.let { optionsSelectedListener ->
            optionsSelectedListener.OptionsAdded(option)
            refreshState()
        }
    }

    fun getSelectedOptions(): MutableList<Option> {
//        println("SearchableGroupMultiSelector publishUpdatedOptions")
//        this.optionsSelectedListener?.let { optionsSelectedListener ->
//            var allSelectedOptions = mutableListOf<Option>()
//            this.data.map { it.optionsList }.forEach {
//                it.filter { it.checked }.let { selectedItems ->
//                    allSelectedOptions.addAll(selectedItems)
//                }
//
//            }
//            optionsSelectedListener.OptionsUpdated(allSelectedOptions)
//        }

        val allSelectedOptions = mutableListOf<Option>()
        this.data.map { it.optionsList }.forEach {
            it.filter { it.checked }.let { selectedItems ->
                allSelectedOptions.addAll(selectedItems)
            }
        }

        return allSelectedOptions

    }

    private fun refreshState() {
        binding.sectionsRoot.removeAllViews()
        if (data.size > 0) {
            if (filterText.length == 0) {
                this.data.forEachIndexed { index, optionsSection ->
                    var checkBoxGroup = CheckBoxGroupWidget(context)
                    checkBoxGroup.setData(optionsSection, filterText)
                    checkBoxGroup.setOptionSelectionChangedListener(object :
                            OptionsSelectedListener {
                        override fun OptionsAdded(newOptionsSelected: Option) {
                            data.forEach {
                                it.optionsList.forEach {
                                    if (it.id == newOptionsSelected.id&&it.text==newOptionsSelected.text) {
                                        it.checked = newOptionsSelected.checked
                                        added(it)
                                    }
                                }
                            }
                        }

//                        override fun OptionsUpdated(allSelectedOptions: List<Option>) {
//                            println(" SearchableGroupMultiSelector OptionsUpdated")
//                            allSelectedOptions.forEach { selectionOption ->
//                                data.forEach {
//                                    it.optionsList.forEach {
//                                        if (it.id == selectionOption.id) {
//                                            it.checked = selectionOption.checked
//                                        }
//                                    }
//                                }
//                            }
//                            publishUpdatedOptions()
//                        }

                        override fun OptionsRemoved(newOptionRemoved: Option) {
                            data.forEach {
                                it.optionsList.forEach {
                                    if (it.id == newOptionRemoved.id&&it.text==newOptionRemoved.text) {
                                        it.checked = newOptionRemoved.checked
                                        removed(it)
                                    }
                                }
                            }
                        }

                    })
                    binding.sectionsRoot.addView(checkBoxGroup)
                }
            } else {
                this.data.forEachIndexed { index, optionsSection ->
                    if (optionsSection.optionsList.any {
                                it.text.contains(
                                        filterText,
                                        ignoreCase = true
                                )
                            }) {
                        var checkBoxGroup = CheckBoxGroupWidget(context)

                        checkBoxGroup.setData(optionsSection, filterText)
                        checkBoxGroup.setOptionSelectionChangedListener(object :
                                OptionsSelectedListener {
                            override fun OptionsAdded(newOptionsSelected: Option) {
                                data.forEach {
                                    it.optionsList.forEach {
                                        if (it.id == newOptionsSelected.id&&it.text==newOptionsSelected.text) {
                                            it.checked = newOptionsSelected.checked
                                            added(it)
                                        }
                                    }
                                }
                            }

//                            override fun OptionsUpdated(allSelectedOptions: List<Option>) {
//                                println(" SearchableGroupMultiSelector OptionsUpdated")
//                                allSelectedOptions.forEach { selectionOption ->
//                                    data.forEach {
//                                        it.optionsList.forEach {
//                                            if (it.id == selectionOption.id) {
//                                                it.checked = selectionOption.checked
//                                            }
//                                        }
//                                    }
//                                }
//                                publishUpdatedOptions()
//                            }

                            override fun OptionsRemoved(newOptionRemoved: Option) {
                                data.forEach {
                                    it.optionsList.forEach {
                                        if (it.id == newOptionRemoved.id&&it.text==newOptionRemoved.text) {
                                            it.checked = newOptionRemoved.checked
                                            removed(it)
                                        }
                                    }
                                }
                            }

                        })
                        binding.sectionsRoot.addView(checkBoxGroup)
                    }
                }
            }

        }
    }

    fun setOptionSelectionChangedListener(optionsSelectedListener: OptionsSelectedListener) {
        this.optionsSelectedListener = optionsSelectedListener
    }

    fun OptionUnchecked(chipId: Int) {
        this.data.forEach {
            it.optionsList.filter { it.id == chipId }.forEach {
                it.checked = false
                this.optionsSelectedListener?.OptionsRemoved(it)
            }
        }
        refreshState()

    }

}

fun OptionsSection.setChecked(isChecked: Boolean) {
    this.optionsList.forEach {
        it.checked = isChecked
    }
    updateCheckedValue()
}

fun OptionsSection.updateCheckedValue() {
    if (this.optionsList.size > 0) {
        this.checked = this.optionsList.filter { it.checked }.size == this.optionsList.size
    } else {
        this.checked = false
    }
}


interface OptionsSelectedListener {
    fun OptionsAdded(newOptionsSelected: Option)

//    fun OptionsUpdated(allSelectedOptions: List<Option>)

    fun OptionsRemoved(newOptionRemoved: Option)
}

data class Option(val id: Int, val text: String, var checked: Boolean = false)

class OptionsSection(
        val sectionText: String? = null,
        var checked: Boolean = false,
        val optionsList: MutableList<Option> = mutableListOf<Option>()
)