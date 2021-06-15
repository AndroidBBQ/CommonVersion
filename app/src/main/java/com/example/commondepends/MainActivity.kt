package com.example.commondepends

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mLineChart: LineChart;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mLineChart = findViewById<LineChart>(R.id.LineChartZttd1)
        init()
        var inflater=getSystemService(Context.LAYOUT_INFLATER_SERVICE)
    }

    private fun init() {
        mLineChart.setExtraOffsets(20f, 20f, 20f, 20f)//设置图表周围与边缘的边距
        mLineChart.setBackgroundColor(Color.parseColor("#ffffff"))
        mLineChart.description.isEnabled = false
//        mLineChart.setScaleMinima(2f, 1f) //X轴放大两倍，Y轴不变
        mLineChart.invalidate()//让柱状图填充数据后刷新
        mLineChart.setBackgroundColor(Color.parseColor("#ffffff"))//设置图表的背景颜色(管用)

        val mLegend = mLineChart.legend
        mLegend.isEnabled = false

        //是否展示网格线
        mLineChart.setDrawGridBackground(false)
        //是否显示边界
        mLineChart.setDrawBorders(false)
        //设置支持触控手势
        mLineChart.setTouchEnabled(true)
        //是否可以拖动
        mLineChart.isDragEnabled = true
        //设置缩放
        mLineChart.setScaleEnabled(false)
        //如果禁用,扩展可以在x轴和y轴分别完成
        mLineChart.setPinchZoom(false)
        mLineChart.isDragDecelerationEnabled = true;//设置手拖拽放开后图是否继续滚动
        mLineChart.dragDecelerationFrictionCoef = 0.9f;//设置继续滚动的速度

        //设置点击折线图的圆点时弹出view
//        val mv = MyMarkerView(context!!)
//        mv.chartView = view!!.LineChartLcsjzqlzs
//        view!!.LineChartLcsjzqlzs.marker = mv

        //配置X轴
        val xAxis = mLineChart.xAxis
//        xAxis.axisMaximum = 9f
//        xAxis.axisMinimum = 1f
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM //设置x标签显示的在底部
        xAxis.setDrawGridLines(false) //不画X轴网格线
        xAxis.setDrawAxisLine(true)
        //自定义X轴标签显示
        val valueFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return when (value.toInt()) {
                    1 -> "卷1"
                    2 -> "卷2"
                    3 -> "卷3"
                    4 -> "卷4"
                    5 -> "卷5"
                    6 -> "卷6"
                    7 -> "卷7"
                    8 -> "卷8"
                    9 -> "卷9"
                    10 -> "卷10"
                    11 -> "卷11"
                    12 -> "卷12"
                    13 -> "卷13"
                    14 -> "卷14"
                    15 -> "卷15"
                    16 -> "卷16"
                    17 -> "卷17"
                    18 -> "卷18"
                    19 -> "卷19"
                    else -> ""
                }
            }
        }
        xAxis.valueFormatter = valueFormatter
        xAxis.textSize = 10f;//设置标签文本大小teal_200
//        xAxis.yOffset = 10f;//设置标签距离图的距离
        xAxis.textColor = ContextCompat.getColor(this, R.color.textColor) //设置标签的文本颜色

        xAxis.axisMinimum = 0.8f
        xAxis.axisMaximum = 19.5f
        mLineChart.setVisibleXRange(0f, 5f)

        //配置左边的Y轴
        val leftAxis = mLineChart.axisLeft
        leftAxis.removeAllLimitLines()
        leftAxis.removeAllLimitLines()
        leftAxis.axisMaximum = 110f //设置最大值
        leftAxis.axisMinimum = 0f //设置最小值
        leftAxis.granularity = 25f //设置以25各单位平分
        leftAxis.labelCount = 5 //设置标签的个数
        leftAxis.setDrawAxisLine(true) //设置不画左边线的第一条线(是否显示左边Y轴)
        leftAxis.enableGridDashedLine(6f, 6f, 0f)//设置虚线
        //自定义标签显示
        leftAxis.valueFormatter = object : ValueFormatter() {
            //取整或者自定义格式
            override fun getFormattedValue(value: Float): String {
//                val value = value.toInt()
//                return value.toString() + "分"
                return when (value.toInt()) {
                    else -> value.toInt().toString()
                }
            }
        }

        //设置标签文本颜色
        leftAxis.textColor = ContextCompat.getColor(this, R.color.textColor)
        mLineChart.axisRight.isEnabled = false //设置右侧Y轴不显示

        //设置数据
        val values: MutableList<Entry> = ArrayList()
        values.add(Entry(1f, 20f))
        values.add(Entry(2f, 35f))
        values.add(Entry(3f, 68f))
        values.add(Entry(4f, 58f))
        values.add(Entry(5f, 73f))
        values.add(Entry(6f, 42f))
        values.add(Entry(7f, 21f))
        values.add(Entry(8f, 56f))
        values.add(Entry(9f, 89f))
        values.add(Entry(10f, 13f))
        values.add(Entry(11f, 13f))
        values.add(Entry(12f, 35f))
        values.add(Entry(13f, 68f))
        values.add(Entry(14f, 58f))
        values.add(Entry(15f, 73f))
        values.add(Entry(16f, 42f))
        values.add(Entry(17f, 21f))
        values.add(Entry(18f, 56f))
        values.add(Entry(19f, 89f))
        val lineDataSet = LineDataSet(values, "正确率走势")
        /*
            LINEAR,直线
            STEPPED,阶梯线
            CUBIC_BEZIER,曲线（圆滑）
            HORIZONTAL_BEZIER,曲线（峰值）
         */
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER //设置折线图的显示模式，可以自行设置上面的值进行查看不同之处
        lineDataSet.color =
            ContextCompat.getColor(this, android.R.color.holo_blue_light) //设置线的颜色
        lineDataSet.lineWidth = 1.5f //设置线的宽度
        lineDataSet.setCircleColor(
            ContextCompat.getColor(
                this,
                android.R.color.holo_blue_light
            )
        ) //设置圆圈的颜色
        lineDataSet.circleHoleColor = ContextCompat.getColor(
            this,
            android.R.color.white
        )//设置圆圈内部洞的颜色
//        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT //设置线数据依赖于左侧y轴
//        lineDataSet.setDrawFilled(true) //设置不画数据覆盖的阴影层
//        lineDataSet.setDrawValues(true) //不绘制线的数据
//        lineDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.textColor) //设置数据的文本颜色，如果不绘制线的数据 这句代码也不用设置了
        lineDataSet.valueTextSize = 15f //如果不绘制线的数据 这句代码也不用设置了
//        lineDataSet.enableDashedHighlightLine(10f, 5f, 0f) //没看出来效果
//        lineDataSet.formLineWidth = 10f //只有lineDataSet.setForm(Legend.LegendForm.LINE);时才有作用 这里我们设置的是圆所以这句代码直接注释
//        lineDataSet.formLineDashEffect = DashPathEffect(
//                floatArrayOf(10f, 5f),
//                0f
//        ) //设置虚线，只有lineDataSet.setForm(Legend.LegendForm.LINE);时才有作用
        lineDataSet.circleRadius = 4f //设置每个折线点的大小
//        lineDataSet.formSize = 15f //设置当前这条线的图例的大小
//        lineDataSet.form = Legend.LegendForm.LINE //设置图例显示为线
        lineDataSet.valueFormatter = object : ValueFormatter() {
            //取整或者自定义格式
            override fun getFormattedValue(value: Float): String {
                val value = value.toInt()
                return value.toString() + "分"
            }
        }
        val lineData = LineData(lineDataSet)
        mLineChart.data = lineData

        //设置曲线填充色 以及 MarkerView
        val drawable = resources.getDrawable(R.drawable.aibk_fade_blue)
        setChartFillDrawable(mLineChart, drawable)
//        setMarkerView(requireContext(), mLineChart, xAxis.valueFormatter)

        mLineChart.animateXY(3000, 3000)     //默认动画
    }

    private fun setChartFillDrawable(lineChart: LineChart, drawable: Drawable?) {
        if (lineChart.data != null && lineChart.data.dataSetCount > 0) {
            val lineDataSet = lineChart.data.getDataSetByIndex(0) as LineDataSet
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true)
            lineDataSet.fillDrawable = drawable
            lineChart.invalidate()
        }
    }


}