package app.ishizaki.ryu.devgoal

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_goal_set.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class GoalSetActivity : AppCompatActivity() {


    val calendar1: Calendar = Calendar.getInstance()
    var goalDate: Date = Date(System.currentTimeMillis())





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_set)


        val db = Room.databaseBuilder(
            applicationContext,
            GoalDatabase::class.java, "goal.db"
        ).build()

        val goalDao = db.goalDao()



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
        }

        startButton.setOnClickListener {

            goalDate = calendar1.time

            GlobalScope.launch {
                withContext(Dispatchers.IO){
                    goalDao.insert(Goal(0, goalSetEditText.text.toString()))
//                    goalDao.update(Goal(0, goalSetEditText.text.toString()))
                }
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, "保存", Toast.LENGTH_LONG).show()
                }
            }


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}