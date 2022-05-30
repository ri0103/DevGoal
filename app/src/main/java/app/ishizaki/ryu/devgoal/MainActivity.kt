package app.ishizaki.ryu.devgoal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import app.ishizaki.ryu.devgoal.fragments.BookmarkFragment
import app.ishizaki.ryu.devgoal.fragments.ChartFragment
import app.ishizaki.ryu.devgoal.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val chartFragment = ChartFragment()
    private val homeFragment = HomeFragment()
    private val bookmarkFragment = BookmarkFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)


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