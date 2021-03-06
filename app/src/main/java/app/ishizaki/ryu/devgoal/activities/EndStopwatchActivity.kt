package app.ishizaki.ryu.devgoal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ishizaki.ryu.devgoal.room.AppDatabase
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.TimerService
import app.ishizaki.ryu.devgoal.Utils
import app.ishizaki.ryu.devgoal.dataclass.Stopwatch
import kotlinx.android.synthetic.main.activity_end_stopwatch.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EndStopwatchActivity : AppCompatActivity() {

    private lateinit var serviceIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_stopwatch)

        val time = intent.getDoubleExtra(StopwatchActivity.EXTRA_MESSAGE, 0.0)

        val db = Utils.getDatabase(applicationContext)


        serviceIntent = Intent(applicationContext, TimerService::class.java)


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
            stopService(serviceIntent)
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
            stopService(serviceIntent)
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
            stopService(serviceIntent)
        }

    }


}