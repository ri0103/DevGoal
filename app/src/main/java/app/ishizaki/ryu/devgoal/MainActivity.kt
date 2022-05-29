package app.ishizaki.ryu.devgoal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val db = Room.databaseBuilder(
        applicationContext,
        GoalDatabase::class.java, "goal.db"
    ).build()

    val goalDao = db.goalDao()
    val goals = goalDao.getAll()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomTestButton.setOnClickListener {
            helloWorld.text = goals[0].goalWord
        }

    }
}