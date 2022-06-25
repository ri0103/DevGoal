package app.ishizaki.ryu.devgoal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.ishizaki.ryu.devgoal.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

//        val sharedPref = this.getSharedPreferences("goalData", Context.MODE_PRIVATE)
//
//        goalSetButton.setOnClickListener {
//            sharedPref.edit().putString("goalText", goalEditText.text.toString()).apply()
////            Toast.makeText(requireContext(), "test", Toast.LENGTH_LONG).show()
//            goalText.text = sharedPref.getString("goalText", "はじめに、目標を設定しよ！")
//        }

    }
}