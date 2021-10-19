package com.oneindia.support.modules.chart

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.explore.support.databinding.WidgetChartBinding
import com.oneindia.support.modules.charts.highcharts.drawChart

class ChartWidget : LinearLayout {

    private var chartWidgetListener: ChartWidgetListener? = null
    private var binding: WidgetChartBinding

    fun setData(chart: Chart){
        binding.idChart.drawChart(chart)
    }

    constructor(context: Context,chart: Chart) : this(context, null){
        setData(chart)
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {

        binding = WidgetChartBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        )
    }

    fun setListener (chartWidgetListener: ChartWidgetListener){
        this.chartWidgetListener = chartWidgetListener
    }

}

interface ChartWidgetListener {
}

sealed class Chart(
    var type : String? = null,
    var title : String? = null,
    var sub_title : String? = null,
    var values_in_percentage : Boolean = false
)

data class PieChart(
    var inputs : List<InputValue>? = null
) : Chart()

data class LineChart(
    var value_suffix : String? = null,
    var xaxis: XAxis = XAxis(),
    var yaxis: YAxis = YAxis()
) : Chart()

data class BarChart(
        var value_suffix : String? = null,
        var xaxis: XAxis = XAxis(),
        var yaxis: YAxis = YAxis()
) : Chart()

data class XAxis(
    var inputs : List<String>? = null
)

data class YAxis(
    var title : String? = null,
    var inputs : List<InputValueArray>? = null
)

data class InputValue(
    var name : String,
    var value : Int
)

data class InputValueArray(
    var name: String? =null,
    var values : List<Int>? =null
)