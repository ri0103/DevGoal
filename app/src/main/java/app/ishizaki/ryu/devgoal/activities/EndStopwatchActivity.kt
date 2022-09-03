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
import com.google.android.material.elevation.SurfaceColors
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

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val onlyDate = calendar.time


        concentrationButton1.setOnClickListener{
            lifecycleScope.launch {
                withContext(Dispatchers.Default){
                    val stopwatch = Stopwatch(0, Date(), onlyDate, time, 0, commitUrlEditText.text.toString())
                    val stopwatchDao = db.stopwatchDao()
                    stopwatchDao.insert(stopwatch)
                }
            }
            finish()
            stopService(serviceIntent)
        }

        concentrationButton2.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Default){
                    val stopwatch = Stopwatch(0, Date(), onlyDate, time, 1, commitUrlEditText.text.toString())
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
                    val stopwatch = Stopwatch(0, Date(), onlyDate, time, 2, commitUrlEditText.text.toString())
                    val stopwatchDao = db.stopwatchDao()
                    stopwatchDao.insert(stopwatch)
                }
            }
            finish()
            stopService(serviceIntent)
        }

    }


}