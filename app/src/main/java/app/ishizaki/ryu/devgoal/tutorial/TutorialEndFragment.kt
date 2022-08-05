package app.ishizaki.ryu.devgoal.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_tutorial_end.*


class TutorialEndFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial_end, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = this.activity?.getSharedPreferences("sharedpref", Context.MODE_PRIVATE)


        endTutorialButton.setOnClickListener {
            sharedPref?.edit()?.putBoolean("initial", false)?.apply()
            val intentMain = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intentMain)
            activity?.finish()

        }
    }


}