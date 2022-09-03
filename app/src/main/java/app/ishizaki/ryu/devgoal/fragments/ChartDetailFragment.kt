package app.ishizaki.ryu.devgoal.fragments

import android.icu.text.Transliterator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import app.ishizaki.ryu.devgoal.ChartDetailCommitAdapter
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.Utils
import kotlinx.android.synthetic.main.fragment_chart_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


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

        val db = Utils.getDatabase(requireContext())
        val commitAdapter = ChartDetailCommitAdapter(requireContext())

        val concentrationList = mutableListOf<Int>()

        val bundle = arguments
        val timeLength = bundle!!.getFloat("LENGTH")
        val dateInLong = bundle.getLong("DATE")

        chartDetailCommitRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commitAdapter
        }

        lifecycleScope.launch(Dispatchers.Default) {
            val stopwatchDao = db.stopwatchDao()
            val all = stopwatchDao.getAll()

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = dateInLong
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val onlyDate = calendar.time
            val sameDateStopwatches = stopwatchDao.getStopwatchByDate(onlyDate)


            sameDateStopwatches.forEach {
                concentrationList.add(it.concentrationLevel)
            }


            withContext(Dispatchers.Main) {
                commitAdapter.update(sameDateStopwatches)

                Toast.makeText(requireContext(), concentrationList.toString(), Toast.LENGTH_SHORT).show()
                concentrationLevelText.text = "開発度" + concentrationList.average().toString()




            }
        }




        val simpleDateFormat = SimpleDateFormat("M/d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateInLong
        detailDateTextView.text = simpleDateFormat.format(calendar.time)



        val timeLengthInDouble = timeLength.toString().toDouble()

        val hours = Math.floor(timeLengthInDouble/60)
        val minutes = timeLengthInDouble - hours*60

//        dayTimeLength.text = (Math.round(timeLength * 10.0)/10.0).toString() + "分"
        dayTimeLength.text = hours.toInt().toString() + "時間" + minutes.toInt().toString() + "分"

        closeChartDetailFragmentButton.setOnClickListener {
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }


    }



}