package app.ishizaki.ryu.devgoal.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.ishizaki.ryu.devgoal.R
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)

        endTutorialButton.setOnClickListener {
            sharedPref.edit().putBoolean("initial", false).apply()
            val intentMain = Intent(this@TutorialActivity, MainActivity::class.java)
            startActivity(intentMain)
            finish()
        }
    }
}