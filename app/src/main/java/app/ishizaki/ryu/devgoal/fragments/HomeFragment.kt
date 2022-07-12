package app.ishizaki.ryu.devgoal.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import app.ishizaki.ryu.devgoal.*
import app.ishizaki.ryu.devgoal.activities.SettingActivity
import app.ishizaki.ryu.devgoal.activities.StopwatchActivity
import app.ishizaki.ryu.devgoal.dataclass.Task
import app.ishizaki.ryu.devgoal.room.AppDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
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

//    private lateinit var recyclerViewAdapter: TaskRecyclerviewAdapter
//    private lateinit var viewModel: HomeFragmentViewModel


    val dateFormat = SimpleDateFormat("期限: yyyy/MM/dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        recyclerViewAdapter = TaskRecyclerviewAdapter()
//        recyclerViewTask.adapter = recyclerViewAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    @SuppressLint("NotifyDataSetChanged")

//    override fun onResume() {
//        super.onResume()
//
//        val db = Room.databaseBuilder(
//            requireContext(),
//            AppDatabase::class.java, "database-goal"
//        ).build()
//
//
//
//    }

    override fun onResume() {
        super.onResume()
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database"
        ).build()
        lifecycleScope.launch(Dispatchers.Default) {
            val goalDao = db.goalDao()
            val all1 = goalDao.getAll()

            withContext(Dispatchers.Main) {
                if (all1.size != 0){
                    goalText.text = "目標: " + all1[0].goalText
                    dueDateText.text = dateFormat.format(all1[0].goalDueDate).toString()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database"
        ).build()

        val taskAdapter = TaskRecyclerviewAdapter(requireContext())

        lifecycleScope.launch(Dispatchers.Default) {
            val taskDao = db.taskDao()
            val all = taskDao.getAll()

                val goalDao = db.goalDao()
                val all1 = goalDao.getAll()

            withContext(Dispatchers.Main) {
                taskAdapter.update(all)
                if (all1.size != 0){
                    goalText.text = "目標: " + all1[0].goalText
                    dueDateText.text = dateFormat.format(all1[0].goalDueDate).toString()
                }
            }
        }


        recyclerViewTask.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = taskAdapter
//            val divider = DividerItemDecoration(requireContext(), VERTICAL)
//            addItemDecoration(divider)
        }



        settingButton.setOnClickListener {
            val intent = Intent (getActivity(), SettingActivity::class.java)
            getActivity()?.startActivity(intent)
        }


        addTaskButton.setOnClickListener {

           val task = Task(0, addTaskEditText.text.toString())
//            viewModel.insertTask(task)

            lifecycleScope.launch(Dispatchers.Default) {
                val taskDao = db.taskDao()

                taskDao.insert(task)

                val tasks: MutableList<Task> = mutableListOf()
                tasks.addAll(taskDao.getAll())
                Log.d("HomeFragment", tasks.toString())

                val all = taskDao.getAll()

                withContext(Dispatchers.Main) {
                    taskAdapter.update(all)
                }
            }
            addTaskEditText.text.clear()
        }

        kaihatuButton.setOnClickListener {
            val intent = Intent (getActivity(), StopwatchActivity::class.java)
            getActivity()?.startActivity(intent)
        }





//        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
//        viewModel.getAllTasksObservers().observe(requireActivity(), androidx.lifecycle.Observer {
//            recyclerViewAdapter.setListData(ArrayList(it))
//            recyclerViewAdapter.notifyDataSetChanged()
//        })



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