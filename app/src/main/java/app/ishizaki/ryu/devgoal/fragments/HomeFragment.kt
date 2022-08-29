package app.ishizaki.ryu.devgoal.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.ishizaki.ryu.devgoal.*
import app.ishizaki.ryu.devgoal.activities.SettingActivity
import app.ishizaki.ryu.devgoal.activities.StopwatchActivity
import app.ishizaki.ryu.devgoal.activities.TutorialActivity
import app.ishizaki.ryu.devgoal.dataclass.Task
import app.ishizaki.ryu.devgoal.room.AppDatabase
import app.ishizaki.ryu.devgoal.viewmodels.GoalViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_task_cell.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId


class HomeFragment : Fragment() {


    val dateFormat = SimpleDateFormat("期日: yyyy/MM/dd")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }



    override fun onResume() {
        super.onResume()

        onViewCreated(requireView(), savedInstanceState = null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = Utils.getDatabase(requireContext())
        val taskAdapter = TaskRecyclerviewAdapter(requireContext())

        lifecycleScope.launch(Dispatchers.Default) {
            val taskDao = db.taskDao()
            val allTask = taskDao.getAll()

            val goalDao = db.goalDao()
            val allGoal = goalDao.getAll()

            withContext(Dispatchers.Main) {
                taskAdapter.update(allTask)
                if (allGoal.isNotEmpty()){
                    goalText.text = "目標: " + allGoal[0].goalText
                    dueDateText.text = dateFormat.format(allGoal[0].goalDueDate).toString()
                }
                if (allTask.isEmpty()){
                    noTaskTextView.isVisible = true
                }
            }
        }


        setFragmentResultListener("requestKey") { key, bundle ->
            val result = bundle.getString("resultKey")


            lifecycleScope.launch(Dispatchers.Default) {
                delay(100)
                val taskDao = db.taskDao()
                val all = taskDao.getAll()
                withContext(Dispatchers.Main) {
                    taskAdapter.update(all)
                }
            }

        }



        recyclerViewTask.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = taskAdapter
        }

        settingButton.setOnClickListener {
            val intent = Intent (requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }

        startTutorialButton.setOnClickListener {
            val intent = Intent (activity, TutorialActivity::class.java)
            startActivity(intent)
        }

       taskAdapter.setOnTaskCellClickListener(
           object : TaskRecyclerviewAdapter.OnTaskCellClickListener{
               override fun onItemClick(task: Task) {

                   lifecycleScope.launch(Dispatchers.Default) {
                       val taskDao = db.taskDao()
                       val all = taskDao.getAll()
                       withContext(Dispatchers.Main) {
                           taskAdapter.update(all)
                           noTaskTextView.isVisible = all.isEmpty()
                       }
                   }

               }
           }
       )

        addTaskButton.setOnClickListener {
                if (addTaskEditText.text?.isNotEmpty() == true){
                    val task = Task(0, addTaskEditText.text.toString(), false, System.currentTimeMillis(), System.currentTimeMillis())
                    lifecycleScope.launch(Dispatchers.Default) {
                        val taskDao = db.taskDao()
                        taskDao.insert(task)
                        val all = taskDao.getAll()
                        withContext(Dispatchers.Main) {
                            taskAdapter.update(all)
                        }
                    }
                    addTaskEditText.text?.clear()
                    noTaskTextView.isVisible = false
                }
        }
        
        addTaskEditText.setOnKeyListener { view, i, keyEvent ->

            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER){
                if (addTaskEditText.text?.isNotEmpty() == true){
                    val task = Task(0, addTaskEditText.text.toString(), false, System.currentTimeMillis(), System.currentTimeMillis())
                    lifecycleScope.launch(Dispatchers.Default) {
                        val taskDao = db.taskDao()
                        taskDao.insert(task)
                        val all = taskDao.getAll()
                        withContext(Dispatchers.Main) {
                            taskAdapter.update(all)
                        }
                    }
                    noTaskTextView.isVisible = false
                }

            }

            if (keyEvent.action == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER){
                addTaskEditText.text?.clear()
        }

            false
        }






        kaihatuButton.setOnClickListener {
            val intent = Intent (requireContext(), StopwatchActivity::class.java)
            startActivity(intent)
        }

        kaihatuButton.setOnLongClickListener{
            val taskDao = db.taskDao()
            val bookmarkDao = db.bookmarkDao()
            lifecycleScope.launch(Dispatchers.Default){
                taskDao.deleteAll()
                bookmarkDao.deleteAll()
            }
            true}




    }




}