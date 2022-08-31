package app.ishizaki.ryu.devgoal.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorMatrix
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.core.view.isVisible
import app.ishizaki.ryu.devgoal.ColorScheme
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.TimerService
import app.ishizaki.ryu.devgoal.databinding.ActivityStopwatchBinding
import com.google.android.material.elevation.SurfaceColors
import kotlinx.android.synthetic.main.activity_stopwatch.*
import kotlinx.coroutines.*
import kotlin.math.roundToInt

class StopwatchActivity : AppCompatActivity() {
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    companion object {
        const val EXTRA_MESSAGE = "app.ishizaki.ryu.devgoal.MESSAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)


        //ストップウォッチがバックグランドで正常稼働するために必要
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            val packageName: String = packageName
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager?
            if (!pm!!.isIgnoringBatteryOptimizations(packageName)) {
                intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
                turnOffBatteryOptimizerMessageCardView.isVisible = true
                turnOffBatteryOptimizerMessageCardView.bringToFront()
            }else{
                turnOffBatteryOptimizerMessageCardView.isVisible = false
            }
        }


        endButton.isEnabled = false

        startStopButton.setOnClickListener { startStopTimer() }
//        binding.resetButton.setOnClickListener { resetTimer() }
        restButton.setOnClickListener {
            stopTimer()
            stCountText.setTextColor(Color.LTGRAY)
            startStopButton.text = "再開"
            kaihatuchuutext.text = "休憩中"

        }
        endButton.setOnClickListener {
            val intent = Intent(applicationContext, EndStopwatchActivity::class.java)
            intent.putExtra(EXTRA_MESSAGE, time)
            finish()
            startActivity(intent)
        }

        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATE))
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIMER_EXTRA, 0.0)
            stCountText.text = getTimeStringFromDouble(time)
            if (intent.getDoubleExtra(TimerService.TIMER_EXTRA, 0.0) == 0.0){
                timerStarted = false
                startStopButton.text = "開始"
                startStopButton.icon = getDrawable(R.drawable.ic_baseline_play_arrow_24)
            }else{
                stCountText.setTextColor(Color.DKGRAY)
                timerStarted = true
                startStopButton.text = "停止"
                startStopButton.icon = getDrawable(R.drawable.ic_baseline_pause_24)
                endButton.isEnabled = true

            }
        }

    }

    private fun getTimeStringFromDouble(time: Double): String{
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 /60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)


    private fun startStopTimer() {
        if (timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        kaihatuchuutext.text = "開発中"
        stCountText.setTextColor(Color.DKGRAY)
        serviceIntent.putExtra(TimerService.TIMER_EXTRA, time)
        startService(serviceIntent)
        startStopButton.text = "停止"
        startStopButton.icon = getDrawable(R.drawable.ic_baseline_pause_24)
        timerStarted = true
    }

    private fun stopTimer() {
        kaihatuchuutext.text = "停止中"
        stopService(serviceIntent)
        startStopButton.text = "開始"
        startStopButton.icon = getDrawable(R.drawable.ic_baseline_play_arrow_24)
        timerStarted = false
    }



}