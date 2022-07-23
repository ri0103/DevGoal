package app.ishizaki.ryu.devgoal.activities

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ishizaki.ryu.devgoal.*
import app.ishizaki.ryu.devgoal.Notification
import app.ishizaki.ryu.devgoal.databinding.ActivityMainBinding
import app.ishizaki.ryu.devgoal.databinding.ActivitySettingBinding
import app.ishizaki.ryu.devgoal.dataclass.Goal
import app.ishizaki.ryu.devgoal.room.AppDatabase
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*

class SettingActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingBinding
    var hourSelected: Int = 0
    var minuteSelected: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        binding.setNotificationButton.setOnClickListener {
//            Toast.makeText(applicationContext, "保存ボタンおした", Toast.LENGTH_SHORT).show()
            scheduleNotification() }


        var yearSelected: Int = 0
        var monthSelected: Int = 0
        var dateSelected: Int = 0

        val c:Calendar = Calendar.getInstance()
        var dueDate = Date()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()

        selectDueDateButton.setOnClickListener {
            DatePickerDialog(
                this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,

                { _, year, monthOfYear, dayOfMonth ->
                    Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }

                    yearSelected = year
                    monthSelected = monthOfYear
                    dateSelected = dayOfMonth

                    selectDueDateButton.text = "${yearSelected}/${monthSelected+1}/${dateSelected}"

                },
                Calendar.YEAR,
                Calendar.MONTH,
                Calendar.DAY_OF_MONTH
            ).apply {
                val now = Date()
                updateDate(now.year+1900, now.month, now.date)
            }.show()
        }

        selectNotificationTimeButton.setOnClickListener{
            TimePickerDialog(
                this,
//                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
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
            }.show()
        }

        goalSetButton.setOnClickListener {
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
//        val title = "開発通知"
//        val message= "今日も開発頑張ろうね！"
//        intent.putExtra(titleExtra, title)
//        intent.putExtra(messageExtra, message)

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
        Toast.makeText(applicationContext, "通知保存しました", Toast.LENGTH_SHORT).show()

//        showAlert()

    }

//    private fun showAlert(){
//
//        AlertDialog.Builder(this)
//            .setTitle("通知設定確認")
//            .setPositiveButton("はい"){_,_ ->}
//            .show()
//    }

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
}