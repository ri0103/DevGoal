package app.ishizaki.ryu.devgoal.activities

import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.*
import app.ishizaki.ryu.devgoal.Notification
import app.ishizaki.ryu.devgoal.dataclass.Goal
import app.ishizaki.ryu.devgoal.dataclass.Notifdata
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*

class SettingActivity : AppCompatActivity() {

    //目標の締め切り
    var yearSelected = 0
    var monthSelected = 0
    var dateSelected = 0
    //通知時刻
    var hourSelected = 0
    var minuteSelected = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        createNotificationChannel()

        //ストップウォッチや通知がバックグランドで正常稼働するために必要
            val intent = Intent()
            val packageName: String = packageName
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager?
            if (!pm!!.isIgnoringBatteryOptimizations(packageName)) {
                turnOffBatteryOptimizerMessageCardView.isVisible = true
                turnOffBatteryOptimizerMessageCardView.bringToFront()
                turnOffBatteryOptimizerMessageCardView.setOnClickListener {
                    intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                }
            }else{
                turnOffBatteryOptimizerMessageCardView.isVisible = false
            }



        val c:Calendar = Calendar.getInstance()
        var dueDate = Date()

        val db = Utils.getDatabase(applicationContext)

        lifecycleScope.launch {
            withContext(Dispatchers.Default){
                val goalDao = db.goalDao()
                val notifdataDao = db.notifdataDao()
                val all = goalDao.getAll()
                val getNotifdata = notifdataDao.getAll()
                if (all.isNotEmpty()){
                    goalEditText.setText(all[0].goalText)
                    val savedDate = all[0].goalDueDate
                    setDueDate(savedDate.year + 1900, savedDate.month, savedDate.date)
                }
                if (getNotifdata.isNotEmpty()){
                    selectNotificationTimeButton.text = "${getNotifdata[0].notifHour}時${getNotifdata[0].notifMin}分"
                }
            }
        }

        selectDueDateButton.setOnClickListener {
            DatePickerDialog(
                this,

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



        saveSettingButton.setOnClickListener {


            if (yearSelected == 0 && monthSelected == 0 && minuteSelected == 0){
                Toast.makeText(applicationContext, "期日を選択してください", Toast.LENGTH_SHORT).show()
            } else {
                c.set(yearSelected, monthSelected, dateSelected)
                dueDate = c.time


                lifecycleScope.launch {
                    withContext(Dispatchers.Default) {
                        val goal = Goal(0, goalEditText.text.toString(), dueDate)
                        val goalDao = db.goalDao()
                        val all = goalDao.getAll()

                        if (all.isEmpty()) {
                            goalDao.insert(goal)
                        } else {
                            goalDao.update(goal)
                        }
                    }
                }

                finish()
            }
        }


        setNotificationButton.setOnClickListener {
            scheduleNotification()
            lifecycleScope.launch(Dispatchers.Default){
                val notifdata = Notifdata(0, hourSelected, minuteSelected)
                val notifdataDao = db.notifdataDao()
                val all = notifdataDao.getAll()
                if (all.isEmpty()){
                    notifdataDao.insert(notifdata)
                } else {
                    notifdataDao.update(notifdata)
                }
            }
        }

        closeSettingButton.setOnClickListener {
            finish()
        }

    }



    private fun createNotificationChannel() {
        val name = "開発催促通知"
        val desc = "毎日開発をするように促す通知です！"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 1000 * 60 * 60 * 24, pendingIntent)
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

        yearSelected = year
        monthSelected = month
        dateSelected = date

        selectDueDateButton.text = "${yearSelected}/${monthSelected+1}/${dateSelected}"
    }
}