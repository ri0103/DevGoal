package app.ishizaki.ryu.devgoal.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.titleExtra
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_chart_detail.*


class ChartDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val timeLength = bundle!!.getFloat("LENGTH")
        val chartLabel = bundle!!.getString("DATE")

        detailDateTextView.text = chartLabel

        val timeLengthInDouble = timeLength.toString().toDouble()

        val hours = Math.floor(timeLengthInDouble/60)
        val minutes = timeLengthInDouble - hours*60

//        dayTimeLength.text = (Math.round(timeLength * 10.0)/10.0).toString() + "分"
        dayTimeLength.text = hours.toInt().toString() + "時間" + minutes.toInt().toString() + "分"

        closeChartDetailFragmentButton.setOnClickListener {
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }


//        val pieData = ArrayList<PieEntry>()
//        val pieColors = ArrayList<Color>()
//
//        pieData.add(PieEntry(36F))
//        pieData.add(PieEntry(48F))
//
//
//
//        val pieDataSet = PieDataSet(pieData, "テスト")
//        val data = PieData(pieDataSet)
//
//        pieDataSet.colors = listOf(Color.RED, Color.GRAY)
//
//        detailTimePieChart.data = data






    }



}