package app.ishizaki.ryu.devgoal.activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceControl
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.fragments.BookmarkFragment
import app.ishizaki.ryu.devgoal.fragments.ChartFragment
import app.ishizaki.ryu.devgoal.fragments.HomeFragment
import app.ishizaki.ryu.devgoal.viewmodels.GoalViewModel
import com.google.android.material.elevation.SurfaceColors
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val chartFragment = ChartFragment()
    private val homeFragment = HomeFragment()
    private val bookmarkFragment = BookmarkFragment()

    private lateinit var goalViewModel: GoalViewModel

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.menu.findItem(R.id.ic_home).setChecked(true)
        replaceFragment(homeFragment)

        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home ->  replaceFragment(homeFragment)
                R.id.ic_chart -> replaceFragment(chartFragment)
                R.id.ic_bookmark -> replaceFragment(bookmarkFragment)
            }
            true
        }


    }
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}