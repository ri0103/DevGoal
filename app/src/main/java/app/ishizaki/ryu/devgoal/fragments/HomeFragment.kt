package app.ishizaki.ryu.devgoal.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.room.Room
import app.ishizaki.ryu.devgoal.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {


//    val sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-task"
        ).build()

        val sharedPref = requireActivity().getSharedPreferences("goalData", Context.MODE_PRIVATE)

        goalText.text = sharedPref.getString("goalText", "デフォルト文字列")


        goalSetButton.setOnClickListener {
            sharedPref.edit().putString("goalText", goalEditText.text.toString()).apply()
            Toast.makeText(requireContext(), "test", Toast.LENGTH_LONG).show()
            goalText.text = sharedPref.getString("goalText", "はじめに、目標を設定しよ！")
        }

        addTaskButton.setOnClickListener {

            lifecycleScope.launch {
                withContext(Dispatchers.Default){
                    val task = Task(0, addTaskEditText.text.toString())
                    val taskDao = db.taskDao()
                    taskDao.insert(task)


                    val tasks: MutableList<Task> = mutableListOf()
                    tasks.addAll(taskDao.getAll())
                    Log.d("HomeFragment", tasks.toString())


                }

            }
            addTaskEditText.text.clear()
        }

        kaihatuButton.setOnClickListener {
            val intent = Intent (getActivity(), StopwatchActivity::class.java)
            getActivity()?.startActivity(intent)
        }

    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment HomeFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            HomeFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}