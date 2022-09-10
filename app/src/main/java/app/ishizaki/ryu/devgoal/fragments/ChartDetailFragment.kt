package app.ishizaki.ryu.devgoal.fragments

import android.icu.text.Transliterator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import app.ishizaki.ryu.devgoal.ChartDetailCommitAdapter
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.Utils
import com.google.android.material.animation.Positioning
import kotlinx.android.synthetic.main.fragment_chart_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.nodes.Range
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
        val durationList = mutableListOf<Int>()
        val totalConcentrationLevelList = mutableListOf<Int>()

        val bundle = arguments
        val timeLength = bundle!!.getFloat("LENGTH")
        val dateInLong = bundle.getLong("DATE")

        chartDetailCommitRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commitAdapter
        }

        lifecycleScope.launch(Dispatchers.Default) {
            val stopwatchDao = db.stopwatchDao()

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
                durationList.add(it.stopwatchDuration.toInt())
            }




            withContext(Dispatchers.Main) {
                commitAdapter.update(sameDateStopwatches)

                var listPosition = 0

                for (i in 0 until concentrationList.size){
                    val totalConcentration = concentrationList[i] * durationList[i]
                    totalConcentrationLevelList.add(totalConcentration)
                }


                val dailyConcentrationScore = totalConcentrationLevelList.sum().toDouble() / durationList.sum().toDouble()

                if (dailyConcentrationScore < 1.4){
                    concentrationScoreImageView.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
                    concentrationScoreImageView.setBackgroundResource(R.drawable.button_bg_3)
                }else if (dailyConcentrationScore < 1.8){
                    concentrationScoreImageView.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24)
                    concentrationScoreImageView.setBackgroundResource(R.drawable.button_bg_23)
                }else if (dailyConcentrationScore < 2.2){
                    concentrationScoreImageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
                    concentrationScoreImageView.setBackgroundResource(R.drawable.button_bg_2)
                }else if (dailyConcentrationScore < 2.6){
                    concentrationScoreImageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
                    concentrationScoreImageView.setBackgroundResource(R.drawable.button_bg_12)
                }else{
                    concentrationScoreImageView.setImageResource(R.drawable.ic_baseline_sentiment_very_satisfied_24)
                    concentrationScoreImageView.setBackgroundResource(R.drawable.button_bg_1)
                }

            }
        }









        val simpleDateFormat = SimpleDateFormat("M/d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateInLong
        detailDateTextView.text = simpleDateFormat.format(calendar.time)



        val timeLengthInDouble = timeLength.toString().toDouble()

        val hours = Math.floor(timeLengthInDouble/60)
        val minutes = timeLengthInDouble - hours*60

        dayTimeLength.text = hours.toInt().toString() + "時間" + minutes.toInt().toString() + "分"

        closeChartDetailFragmentButton.setOnClickListener {
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }


    }



}