package app.ishizaki.ryu.devgoal

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_goal_set.*
import java.util.*

class GoalSetActivity : AppCompatActivity() {


    var yearToSave: Int = 0
    var monthToSave: Int = 0
    var dateToSave: Int = 0
    val calendar1: Calendar = Calendar.getInstance()
    var goalDate: Date = Date(System.currentTimeMillis())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_set)



        dateSelectButton1.setOnClickListener{
            DatePickerDialog(
                this,

                { _, year, monthOfYear, dayOfMonth ->
                    Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }

                    calendar1.set(year, monthOfYear, dayOfMonth)

                    dateSelectButton1.text = "${year}年${monthOfYear+1}月${dayOfMonth}日"

                },
                calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH)
            ).apply {
            }.show()

            goalDate = calendar1.time
        }

        startButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}