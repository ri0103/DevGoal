package app.ishizaki.ryu.devgoal.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.R
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
        val isInitial = sharedPref.getBoolean("initial", true)

        CoroutineScope(Dispatchers.Main).launch{


            if (isInitial){
                val intentTutorial = Intent(this@SplashActivity, TutorialActivity::class.java)
                startActivity(intentTutorial)
                finish()
            } else {
                val intentMain = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intentMain)
                finish()
            }
        }

    }
}