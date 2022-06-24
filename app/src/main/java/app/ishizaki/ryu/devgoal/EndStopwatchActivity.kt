package app.ishizaki.ryu.devgoal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ishizaki.ryu.devgoal.databinding.ActivityEndStopwatchBinding
import kotlinx.android.synthetic.main.activity_end_stopwatch.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EndStopwatchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_stopwatch)

        val time = intent.getDoubleExtra(StopwatchActivity.EXTRA_MESSAGE, 0.0)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-stopwatch"
        ).build()

        concentrationButton1.setOnClickListener{
            lifecycleScope.launch {
                withContext(Dispatchers.Default){
                    val stopwatch = Stopwatch(0, Date(), time, 0)
                    val stopwatchDao = db.stopwatchDao()
                    stopwatchDao.insert(stopwatch)
//                    val times: MutableList<Stopwatch> = mutableListOf()
//                    times.addAll(stopwatchDao.getAll())
//                    Log.d("EndStopwatchActivity", times.toString())
                }
            }
            finish()



        }

        concentrationButton2.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Default){
                    val stopwatch = Stopwatch(0, Date(), time, 1)
                    val stopwatchDao = db.stopwatchDao()
                    stopwatchDao.insert(stopwatch)
                }
            }
            finish()
        }

        concentrationButton3.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Default){
                    val stopwatch = Stopwatch(0, Date(), time, 2)
                    val stopwatchDao = db.stopwatchDao()
                    stopwatchDao.insert(stopwatch)
                }
            }
            finish()
        }

    }


}