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
import kotlinx.android.synthetic.main.fragment_chart.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
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
            AppDatabase::class.java, "database-stopwatch"
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
                if (stopwatchDurations.keys.contains(stopwatch.endDateTime)) {
                    //mapの更新
                    stopwatchDurations[stopwatch.endDateTime]?.add(stopwatch)
                } else {
                    //mapの追加
                    stopwatchDurations.put(stopwatch.endDateTime, mutableListOf(stopwatch))
                }
                Log.d("ChartFragment", stopwatchDurations[stopwatch.endDateTime].toString())
            }



            withContext(Dispatchers.Main){



                stopwatchDurations.keys.forEachIndexed { index, key ->

                        var total = 0
                        val list = stopwatchDurations[key] ?: listOf()
                        for (stopwatch in list) {
                            total += stopwatch.stopwatchDuration.toInt()
                        }
                        chartData.add(BarEntry(index.toFloat(), total.toFloat()))

                }


                val chartDataSet = BarDataSet(chartData, "作業時間（分）")
                timeBarChart.animateY(1000)
                val data = BarData(chartDataSet)
                timeBarChart.data = data
            }


        }



//        chartData.add(BarEntry(stopwatchDurations.key, total.toFloat() ))

    }


}