package com.explore.support.modules.charts.highcharts

import com.explore.support.modules.chart.BarChart
import com.explore.support.modules.chart.Chart
import com.explore.support.modules.chart.PieChart
import com.explore.support.modules.charts.highcharts.ChartTypes.PIE_DONUT_CHART
import com.explore.support.modules.charts.highcharts.ChartTypes.PIE_NORMAL_CHART
import com.highsoft.highcharts.Common.HIChartsClasses.*
import com.highsoft.highcharts.Core.HIChartView
import java.util.*
import kotlin.collections.ArrayList


object ChartTypes{
    const val BAR_STACK_CHART = "bar_stack_chart"
    const val PIE_NORMAL_CHART = "pie_normal_chart"
    const val PIE_DONUT_CHART = "pie_donut_chart"
}

fun HIChartView.drawChart(chart: Chart){
    when(chart){
        is BarChart -> {
            drawBarChart(chart)
        }
        is PieChart -> {
            drawPieChart(chart)
        }
    }
}

fun HIChartView.drawBarChart(barChart: BarChart) {
    val options = HIOptions()

    val hiChart = HIChart()
    hiChart.type = "bar"
    options.chart = hiChart

    val title = HITitle()
    title.text = barChart?.title
    options.title = title

    val subtitle = HISubtitle()
    subtitle.text = barChart?.sub_title
    options.subtitle = subtitle

    val xaxis = HIXAxis()
    val categories = barChart?.xaxis?.inputs?.map { it }?.toCollection(ArrayList<String>())

    xaxis.categories = categories
    options.xAxis = object : ArrayList<HIXAxis?>() {
        init {
            add(xaxis)
        }
    }

    val yaxis = HIYAxis()
    yaxis.min = 0
    yaxis.title = HITitle()
    yaxis.title.text = barChart?.yaxis?.title
    yaxis.title.align = "high"
    yaxis.labels = HILabels()
    yaxis.labels.overflow = "justify"
    options.yAxis = object : ArrayList<HIYAxis?>() {
        init {
            add(yaxis)
        }
    }

    val yAxis = HIYAxis()
    yAxis.min = 0
    yAxis.title = HITitle()
    yAxis.title.text = "Jobs count"
    options.yAxis = object : ArrayList<HIYAxis?>() {
        init {
            add(yAxis)
        }
    }

    val legend = HILegend()
    legend.reversed = true
    options.legend = legend

    val plotOptions = HIPlotOptions()
    plotOptions.series = HISeries()
    plotOptions.series.stacking = "normal"
    options.plotOptions = plotOptions

    val tooltip = HITooltip()
    tooltip.valueSuffix = barChart?.value_suffix
    options.tooltip = tooltip

//    val plotOptions = HIPlotOptions()
//    plotOptions.bar = HIBar()
//    plotOptions.bar.dataLabels = HIDataLabels()
//    plotOptions.bar.dataLabels.enabled = true
//    options.plotOptions = plotOptions

//    val legend = HILegend()
//    legend.layout = "vertical"
//    legend.align = "right"
//    legend.verticalAlign = "top"
//    legend.x = -40
//    legend.y = 80
//    legend.floating = true
//    legend.borderWidth = 1
//    legend.backgroundColor = HIColor.initWithHexValue("FFFFFF")
//    legend.shadow = true
//    options.legend = legend

    val credits = HICredits()
    credits.enabled = false
    options.credits = credits

    var bars = mutableListOf<HIBar>()
    barChart?.yaxis?.inputs?.forEach {
        val bar = HIBar()
        bar.name = it.name
        val barData = it.values
        bar.data = barData?.toCollection(ArrayList<Int>())
        bars.add(bar)
    }

    options.setSeries(ArrayList(bars))

    this.options = options
}

fun HIChartView.drawPieChart(pieChart: PieChart) {
    val options = HIOptions()

    val hiChart = HIChart()
    hiChart.type = "pie"
    hiChart.options3d = HIOptions3d()
    hiChart.options3d.enabled = true
    hiChart.options3d.alpha = 45
    hiChart.options3d.beta = 0
    options.chart = hiChart

    val title = HITitle()
    title.text = pieChart?.title
    options.title = title

    val tooltip = HITooltip()
//        tooltip.pointFormat = "{series.name}: <b>{point.percentage:.1f}%</b>"
    options.tooltip = tooltip

    val plotOptions = HIPlotOptions()
    plotOptions.pie = HIPie()
    when(pieChart.type){
        PIE_NORMAL_CHART -> {
            plotOptions.pie.allowPointSelect = true
            plotOptions.pie.cursor = "pointer"
            plotOptions.pie.depth = 35
            plotOptions.pie.dataLabels = HIDataLabels()
            plotOptions.pie.dataLabels.enabled = true
            plotOptions.pie.dataLabels.format = "{point.name}"
        }
        PIE_DONUT_CHART -> {
            plotOptions.pie.innerSize = 100
            plotOptions.pie.depth = 45
        }
    }
    options.plotOptions = plotOptions

    val series1 = HIPie()
    series1.name = "Count"

    var datas = mutableListOf<Array<Any>>()
    pieChart?.inputs?.forEach {
//        val data = arrayOf(it.name,it.value)
//        datas.add(data)
    }
    series1.data = ArrayList(datas)

    options.setSeries(ArrayList(Arrays.asList(series1)))
    this.options = options
}