package app.ishizaki.ryu.devgoal.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.dataclass.Stopwatch
import app.ishizaki.ryu.devgoal.room.AppDatabase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_chart.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList

class ChartFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database"
        ).build()

        val chartData = ArrayList<BarEntry>()
        val stopwatchDurations: MutableMap<Date, MutableList<Stopwatch>> = mutableMapOf()

        lifecycleScope.launch(Dispatchers.Default) {
            val stopwatchDao = db.stopwatchDao()
            val all = stopwatchDao.getAll()
//            val stopwatchdatas: MutableList<Stopwatch> = mutableListOf()
//            stopwatchdatas.addAll(all)
//            Log.d("ChartFragment", stopwatchdatas.toString())





            for (stopwatch in all) {

                val calendar = Calendar.getInstance()
                calendar.time = stopwatch.endDateTime

//                calendar.set(Calendar.HOUR_OF_DAY, 0)
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

//                Log.d("ChartFragment", stopwatchDurations[calendar.time].toString())
            }

            withContext(Dispatchers.Main){

                stopwatchDurations.keys.forEachIndexed { index, key ->
                        var total = 0
                        val list = stopwatchDurations[key] ?: listOf()
                        for (stopwatch in list) {
                            total += stopwatch.stopwatchDuration.toInt()
                        }
                        chartData.add(BarEntry(index.toFloat(), total.toFloat()/60))
                }


                val chartDataSet = BarDataSet(chartData, "作業時間（分）")
                timeBarChart.animateY(480)
                val data = BarData(chartDataSet)

                timeBarChart.data = data
//                val labels = arrayOf("いち", "に")
//                timeBarChart.xAxis.setValueFormatter(IndexAxisValueFormatter(labels))
//
//                timeBarChart.xAxis.apply {
//                    isEnabled = false
//                }
                timeBarChart.axisLeft.apply {
                    isEnabled = false
                }
                timeBarChart.axisRight.apply {
                    isEnabled = false
                }
                timeBarChart.description.isEnabled = false
            }
        }
    }


}