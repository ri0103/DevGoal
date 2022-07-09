package app.ishizaki.ryu.devgoal.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.dataclass.Goal
import app.ishizaki.ryu.devgoal.room.AppDatabase
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

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
                AlertDialog.THEME_DEVICE_DEFAULT_DARK,

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


        }

    }
}