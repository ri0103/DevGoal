package app.ishizaki.ryu.devgoal.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.TutorialPagerAdapter
import app.ishizaki.ryu.devgoal.tutorial.*
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)


        val fragmentList = arrayListOf<Fragment>(
            Tutorial1Fragment(),
            Tutorial2Fragment(),
            Tutorial3Fragment(),
            Tutorial4Fragment(),
            Tutorial5Fragment(),
            Tutorial6Fragment(),
            Tutorial7Fragment(),
            TutorialEndFragment()
        )


        val pagerAdapter = TutorialPagerAdapter(supportFragmentManager, fragmentList)
        with(viewPager){
            adapter = pagerAdapter
            dotsIndicator.attachTo(this)
        }



    }
}