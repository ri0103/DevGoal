package app.ishizaki.ryu.devgoal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ishizaki.ryu.devgoal.AppDatabase
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.Stopwatch
import app.ishizaki.ryu.devgoal.Task
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.fragment_chart.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartData = ArrayList<BarEntry>()
        chartData.add(BarEntry(1f, 1000f))
        chartData.add(BarEntry(2f, 2000f))
        chartData.add(BarEntry(3f, 2500f))
        chartData.add(BarEntry(4f, 2750f))

        val chartDataSet = BarDataSet(chartData, "グラフテスト")
        timeBarChart.animateY(1000)
        val data = BarData(chartDataSet)
        timeBarChart.data = data


    }


}