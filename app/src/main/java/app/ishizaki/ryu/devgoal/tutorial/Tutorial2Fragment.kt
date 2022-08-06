package app.ishizaki.ryu.devgoal.tutorial

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.ishizaki.ryu.devgoal.R
import kotlinx.android.synthetic.main.fragment_tutorial2.*


class Tutorial2Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            tutorial2VideoView.setVideoPath("android.resource://" + activity?.packageName + "/" + R.raw.tutorial_video_test)
            tutorial2VideoView.start()
            tutorial2VideoView.setOnCompletionListener {
                tutorial2VideoView.start()
            }


    }

}