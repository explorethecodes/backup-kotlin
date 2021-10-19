package com.oneindia.support.modules.charts.highcharts

import com.highsoft.highcharts.Common.HIChartsClasses.*
import com.highsoft.highcharts.Common.HIColor
import com.highsoft.highcharts.Core.HIChartView
import com.oneindia.support.modules.chart.BarChart
import com.oneindia.support.modules.chart.Chart
import com.oneindia.support.modules.chart.LineChart
import com.oneindia.support.modules.chart.PieChart
import com.oneindia.support.modules.charts.highcharts.ChartTypes.BAR_BASIC_CHART
import com.oneindia.support.modules.charts.highcharts.ChartTypes.BAR_STACK_CHART
import com.oneindia.support.modules.charts.highcharts.ChartTypes.LINE_DATA_LABELS_CHART
import com.oneindia.support.modules.charts.highcharts.ChartTypes.PIE_BASIC_CHART
import com.oneindia.support.modules.charts.highcharts.ChartTypes.PIE_BASIC_CHART_3D
import com.oneindia.support.modules.charts.highcharts.ChartTypes.PIE_DONUT_CHART
import com.oneindia.support.modules.charts.highcharts.ChartTypes.PIE_DONUT_CHART_3D
import com.oneindia.support.modules.charts.highcharts.ChartTypes.PIE_SEMI_DONUT_CHART
import java.util.*
import kotlin.collections.ArrayList


object ChartTypes{
    const val LINE_BASIC_CHART = "line_basic_chart"
    const val LINE_DATA_LABELS_CHART = "line_data_labels_chart"

    const val BAR_BASIC_CHART = "bar_basic_chart"
    const val BAR_STACK_CHART = "bar_stack_chart"

    const val PIE_BASIC_CHART = "pie_basic_chart"
    const val PIE_DONUT_CHART = "pie_donut_chart"
    const val PIE_SEMI_DONUT_CHART = "pie_semi_donut_chart"

    const val PIE_BASIC_CHART_3D = "pie_basic_chart_3d"
    const val PIE_DONUT_CHART_3D = "pie_donut_chart_3d"
}

fun HIChartView.drawChart(chart: Chart){
    when(chart){
        is LineChart -> {
            drawLineChart(chart)
        }
        is BarChart -> {
            drawBarChart(chart)
        }
        is PieChart -> {
            drawPieChart(chart)
        }
    }
}

fun HIChartView.drawLineChart(lineChart: LineChart) {

    val options = HIOptions()

    val exporting = HIExporting()
    exporting.enabled = false
    options.exporting = exporting

    val title = HITitle()
    title.text = lineChart?.title
    options.title = title

    val subtitle = HISubtitle()
    subtitle.text = lineChart?.sub_title
    options.subtitle = subtitle

    val xaxis = HIXAxis()
    val categories = lineChart?.xaxis?.inputs?.map { it }?.toCollection(ArrayList<String>())

    xaxis.categories = categories
    options.xAxis = object : ArrayList<HIXAxis?>() {
        init {
            add(xaxis)
        }
    }

    val yaxis = HIYAxis()
    yaxis.min = 0
    yaxis.title = HITitle()
    yaxis.title.text = lineChart?.yaxis?.title
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
    yAxis.title.text = lineChart.title
    options.yAxis = object : ArrayList<HIYAxis?>() {
        init {
            add(yAxis)
        }
    }

    val legend = HILegend()


    val plotOptions = HIPlotOptions()


    val tooltip = HITooltip()

    when(lineChart.type){
        LINE_DATA_LABELS_CHART -> {
            plotOptions.line = HILine()
            plotOptions.line.dataLabels = HIDataLabels()
            plotOptions.line.dataLabels.enabled = true
            plotOptions.line.enableMouseTracking = false
        }
    }

//    options.legend = legend
    options.plotOptions = plotOptions
//    options.tooltip = tooltip

    val credits = HICredits()
    credits.enabled = false
    options.credits = credits

    var bars = mutableListOf<HILine>()
    lineChart?.yaxis?.inputs?.forEach {
        val line = HILine()
        line.name = it.name
        val barData = it.values
        line.data = barData?.toCollection(ArrayList<Int>())
        bars.add(line)
    }

    options.setSeries(ArrayList(bars))

    this.options = options
}

fun HIChartView.drawBarChart(barChart: BarChart) {
    val options = HIOptions()

    val exporting = HIExporting()
    exporting.enabled = false
    options.exporting = exporting

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
    yAxis.title.text = barChart.title
    options.yAxis = object : ArrayList<HIYAxis?>() {
        init {
            add(yAxis)
        }
    }

    val legend = HILegend()


    val plotOptions = HIPlotOptions()


    val tooltip = HITooltip()



//    val plotOptions = HIPlotOptions()

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
    when(barChart.type){
        BAR_BASIC_CHART -> {
            plotOptions.bar = HIBar()
            plotOptions.bar.dataLabels = HIDataLabels()
            plotOptions.bar.dataLabels.enabled = true

            legend.layout = "vertical"
            legend.align = "right"
            legend.verticalAlign = "top"
            legend.x = -40
            legend.y = 80
            legend.floating = true
            legend.borderWidth = 1
            legend.backgroundColor = HIColor.initWithHexValue("FFFFFF")
            legend.shadow = true
        }

        BAR_STACK_CHART -> {
            legend.reversed = true

            plotOptions.series = HISeries()
            plotOptions.series.stacking = "normal"

            tooltip.valueSuffix = barChart?.value_suffix
        }
    }

    options.legend = legend
    options.plotOptions = plotOptions
    options.tooltip = tooltip

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

    val exporting = HIExporting()
    exporting.enabled = false
    options.exporting = exporting

    val hiChart = HIChart()
    hiChart.type = "pie"

    val title = HITitle()
    title.text = pieChart?.title

    val subtitle = HISubtitle()
    subtitle.text = pieChart?.sub_title
    options.subtitle = subtitle

    val tooltip = HITooltip()
    tooltip.pointFormat = "{series.name}: <b>{point.percentage:.1f}%</b>"
//    if (pieChart.values_in_percentage){
//        tooltip.pointFormat = "{series.name}: <b>{point.percentage:.1f}%</b>"
//    }
    options.tooltip = tooltip

    val plotOptions = HIPlotOptions()
    plotOptions.pie = HIPie()

    val series1 = HIPie()

    when(pieChart.type){
        PIE_BASIC_CHART -> {
            plotOptions.pie.allowPointSelect = true
            plotOptions.pie.cursor = "pointer"
            plotOptions.pie.depth = 35
            plotOptions.pie.dataLabels = HIDataLabels()
            plotOptions.pie.dataLabels.enabled = true
            plotOptions.pie.dataLabels.format = "{point.name}: <b>{point.percentage:.1f}%</b>"
        }
        PIE_BASIC_CHART_3D -> {
            plotOptions.pie.allowPointSelect = true
            plotOptions.pie.cursor = "pointer"
            plotOptions.pie.depth = 35
            plotOptions.pie.dataLabels = HIDataLabels()
            plotOptions.pie.dataLabels.enabled = true
            plotOptions.pie.dataLabels.format = "{point.name}: <b>{point.percentage:.1f}%</b>"

            hiChart.options3d = HIOptions3d()
            hiChart.options3d.enabled = true
            hiChart.options3d.alpha = 45
            hiChart.options3d.beta = 0
        }

        PIE_DONUT_CHART -> {
            plotOptions.pie.innerSize = 100
            plotOptions.pie.depth = 45
        }
        PIE_DONUT_CHART_3D -> {
            plotOptions.pie.innerSize = 100
            plotOptions.pie.depth = 45
            hiChart.options3d = HIOptions3d()
            hiChart.options3d.enabled = true
            hiChart.options3d.alpha = 45
            hiChart.options3d.beta = 0
        }

        PIE_SEMI_DONUT_CHART -> {

            hiChart.setType("pie");
            hiChart.setBackgroundColor(null);
            hiChart.setPlotBorderWidth(0);
            hiChart.setPlotShadow(false);

//            title.setText("Browser<br>shares<br>2015");
//            title.setAlign("center");
//            title.setVerticalAlign("middle");
//            title.setY(40);

//            tooltip.setPointFormat("{series.name}: <b>{point.percentage:.1f}%</b>")

            plotOptions.pie.dataLabels = HIDataLabels()
            plotOptions.pie.dataLabels.enabled = true
            plotOptions.pie.dataLabels.distance = -50
            plotOptions.pie.dataLabels.style = HIStyle()
            plotOptions.pie.dataLabels.align = "center"
            plotOptions.pie.dataLabels.style.fontWeight = "bold"
            plotOptions.pie.dataLabels.style.color = "white"
            plotOptions.pie.startAngle = -90
            plotOptions.pie.endAngle = 90
            val centerList = arrayOf("50%", "75%")
            plotOptions.pie.center = ArrayList(Arrays.asList(centerList))

            series1.setName(pieChart.title);
            series1.setInnerSize("50%");

        }
    }

    plotOptions.pie.dataLabels?.format = "{point.name}: <br><b>    {point.percentage:.1f}%</b>"

    options.title = title

    options.chart = hiChart
    options.plotOptions = plotOptions

    if (pieChart.values_in_percentage){
        series1.name = "Percentage"
    } else {
        series1.name = "Count"
    }

    val datas = mutableListOf<Array<Any>>()
    pieChart?.inputs?.forEach {
        val data = arrayOf(it.name, it.value)
//        datas.add(data)
    }
    series1.data = ArrayList(datas)

    options.setSeries(ArrayList(listOf(series1)))
    this.options = options
}

/////////////////////////////////////////////////////////