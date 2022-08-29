package app.ishizaki.ryu.devgoal.fragments

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.dataclass.Goal
import app.ishizaki.ryu.devgoal.dataclass.Stopwatch
import app.ishizaki.ryu.devgoal.room.AppDatabase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.color.DynamicColors
import kotlinx.android.synthetic.main.fragment_chart.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

class ChartFragment : Fragment(), OnChartValueSelectedListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    val dateData: MutableList<Date> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database"
        ).build()

        val chartData = ArrayList<BarEntry>()
        val stopwatchDurations: MutableMap<Date, MutableList<Stopwatch>> = mutableMapOf()
        val weeklyTotal: MutableMap<Date, MutableList<Stopwatch>> = mutableMapOf()

        lifecycleScope.launch(Dispatchers.Default) {
            val stopwatchDao = db.stopwatchDao()
            val all = stopwatchDao.getAll()

            for (stopwatch in all) {

                val calendar = Calendar.getInstance()
                calendar.time = stopwatch.endDateTime

                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                    if (stopwatchDurations.keys.contains(calendar.time)) {
                    //mapの更新
                    stopwatchDurations[calendar.time]?.add(stopwatch)
                } else {
                    //mapの追加
                    stopwatchDurations.put(calendar.time, mutableListOf(stopwatch))

                }

            }

            withContext(Dispatchers.Main){

                stopwatchDurations.keys.forEachIndexed { index, key ->
                        var total = 0
                        val list = stopwatchDurations[key] ?: listOf()
                        for (stopwatch in list) {
                            total += stopwatch.stopwatchDuration.toInt()
                        }
                        chartData.add(BarEntry(index.toFloat(), total.toFloat()/60))
                        dateData.add(key)
                }

                val xAxisValueFormatter = object  : ValueFormatter(){
                    private var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("M/d", Locale.getDefault())
                    override fun getFormattedValue(value: Float): String {
                        val chartDate = dateData[value.toInt()]
                        return simpleDateFormat.format(chartDate)
                    }
                }

                val chartDataSet = BarDataSet(chartData, "作業時間（分）")
//                chartDataSet.color = Color.parseColor("#9c9c9c")

                chartDataSet.color = R.color.md_theme_light_primary
                timeBarChart.animateY(480)
                timeBarChart.axisLeft.axisMinimum = 0F
                val data = BarData(chartDataSet)

                timeBarChart.data = data
                timeBarChart.xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = xAxisValueFormatter
                    labelCount = dateData.size
//                    mEntryCount = dateData.size
                    setDrawGridLines(false)
                }
                timeBarChart.axisRight.apply {
                    isEnabled = false
                }
                timeBarChart.axisLeft.apply {
                    setDrawGridLines(false)
                }

                timeBarChart.description.isEnabled = false

                timeBarChart.setOnChartValueSelectedListener(this@ChartFragment)
            }
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("M/d", Locale.getDefault())
        val chartTimeLength = e?.y
        val chartIndex = e?.x
        val chartLabel = simpleDateFormat.format(dateData[chartIndex!!.toInt()])


        val chartDetailFragment = ChartDetailFragment()
        val bundle = Bundle()
        bundle.putFloat("LENGTH", chartTimeLength!!)
        bundle.putString("DATE", chartLabel)
        chartDetailFragment.arguments = bundle
        val transaction = (requireContext() as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.add(R.id.chart_detail_container, chartDetailFragment).commit()
    }



    override fun onNothingSelected() {

    }


}