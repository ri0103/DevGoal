package app.ishizaki.ryu.devgoal.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.dataclass.Goal
import app.ishizaki.ryu.devgoal.room.AppDatabase
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-goal"
        ).build()

        goalSetButton.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Default){
                    val goal = Goal(0, goalEditText.text.toString())
                    val goalDao = db.goalDao()
                    goalDao.insert(goal)
                }
            }
            Toast.makeText(applicationContext, "test", Toast.LENGTH_LONG).show()
        }

    }
}