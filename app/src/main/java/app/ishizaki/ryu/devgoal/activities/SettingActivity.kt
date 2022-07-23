package app.ishizaki.ryu.devgoal.activities

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.*
import app.ishizaki.ryu.devgoal.Notification
import app.ishizaki.ryu.devgoal.databinding.ActivitySettingBinding
import app.ishizaki.ryu.devgoal.dataclass.Goal
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*

class SettingActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingBinding
    //目標の締め切り
    var yearSelected = 0
    var monthSelected = 0
    var dateSelected = 0
    //通知時刻
    var hourSelected = 0
    var minuteSelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()


        val c:Calendar = Calendar.getInstance()
        var dueDate = Date()

        val db = Utils.getDatabase(applicationContext)

        lifecycleScope.launch {
            withContext(Dispatchers.Default){
                val goalDao = db.goalDao()
                val all = goalDao.getAll()
                if (all.isNotEmpty()){
                    goalEditText.setText(all[0].goalText)
                    val savedDate = all[0].goalDueDate
                    setDueDate(savedDate.year + 1900, savedDate.month, savedDate.date)

                }
            }
        }

        selectDueDateButton.setOnClickListener {
            DatePickerDialog(
                this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,

                { _, year, monthOfYear, dayOfMonth ->
                    setDueDate(year, monthOfYear, dayOfMonth)
                },
                Calendar.YEAR,
                Calendar.MONTH,
                Calendar.DAY_OF_MONTH
            ).apply {
                val now = LocalDate.now()
                updateDate(now.year, now.monthValue - 1, now.dayOfMonth)
            }.show()
        }

        selectNotificationTimeButton.setOnClickListener{
            TimePickerDialog(
                this,
                AlertDialog.THEME_HOLO_LIGHT,

                { _, hour, minute ->

                    hourSelected = hour
                    minuteSelected = minute

                    selectNotificationTimeButton.text = "${hourSelected}時${minuteSelected}分"

                },
                Calendar.HOUR_OF_DAY,
                Calendar.MINUTE,
                true
            ).apply {
                val now = Date()
                updateTime(now.hours, now.minutes)
            }.show()
        }

        binding.saveSettingButton.setOnClickListener {
            c.set(yearSelected, monthSelected, dateSelected)
            dueDate = c.time


            lifecycleScope.launch {
                withContext(Dispatchers.Default){
                    val goal = Goal(0, goalEditText.text.toString(), dueDate)
                    val goalDao = db.goalDao()
                    val all = goalDao.getAll()

                    if (all.isEmpty()){
                        goalDao.insert(goal)
                    } else {
                        goalDao.update(goal)
                    }
                }
            }

            scheduleNotification()

            finish()
        }

        closeSettingButton.setOnClickListener {
            finish()
        }

    }

    private fun createNotificationChannel() {
        val name = "開発催促通知"
        val desc = "A description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
//        val notifyIntent = Intent(this, StopwatchActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis, 1000 * 60 * 60 * 24, pendingIntent)
    }


    private fun getTime(): Long {
        val minute = minuteSelected
        val hour = hourSelected
        val day = LocalDate.now().dayOfMonth
        val month = LocalDate.now().monthValue - 1
        val year = LocalDate.now().year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun setDueDate(year: Int, month: Int, date: Int) {
//        Calendar.getInstance().apply { set(year, month, date) }

        yearSelected = year
        monthSelected = month
        dateSelected = date

        selectDueDateButton.text = "${yearSelected}/${monthSelected+1}/${dateSelected}"
    }
}