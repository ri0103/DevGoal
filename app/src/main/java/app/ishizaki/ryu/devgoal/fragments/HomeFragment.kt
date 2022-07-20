package app.ishizaki.ryu.devgoal.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

       taskAdapter.setOnTaskCellClickListener(
           object : TaskRecyclerviewAdapter.OnTaskCellClickListener{
               override fun onItemClick(task: Task) {

                   lifecycleScope.launch(Dispatchers.Default) {
                       val taskDao = db.taskDao()
                       val all = taskDao.getAll()
                       withContext(Dispatchers.Main) {
                           taskAdapter.update(all)
                       }
                   }
                   recyclerViewTask.apply {
                       layoutManager = LinearLayoutManager(activity)
                       adapter = taskAdapter
                   }
               }
           }
       )


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










//        lifecycleScope.launch(Dispatchers.Default) {
//
//
//
//            val taskDao = db.taskDao()
//            val all = taskDao.getAll()
//
//            withContext(Dispatchers.Main) {
//                fun getSwipeToDismissTouchHelper(adapter: TaskRecyclerviewAdapter)=
//                    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//                        ItemTouchHelper.ACTION_STATE_IDLE,
//                        ItemTouchHelper.RIGHT
//                    ){
//
//                        override fun onMove(
//                            recyclerView: RecyclerView,
//                            viewHolder: RecyclerView.ViewHolder,
//                            target: RecyclerView.ViewHolder
//                        ): Boolean {
//                            return false
//                        }
//
//                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                            val id = all[viewHolder.adapterPosition]?.id
////                            val task = realm.where(Coverage::class.java).equalTo("id", id).findFirst()
//                            val task = all[id]
//                            if (task != null) {
//                                taskDao.delete(task)
//                            }
//
////                        coverageList = realm.where(Coverage::class.java).findAll().sort("createdTime", Sort.ASCENDING)
//                            adapter.notifyItemRemoved(viewHolder.adapterPosition)
//                        }
//
//                        @SuppressLint("ResourceType")
//                        override fun onChildDraw(
//                            c: Canvas,
//                            recyclerView: RecyclerView,
//                            viewHolder: RecyclerView.ViewHolder,
//                            dX: Float,
//                            dY: Float,
//                            actionState: Int,
//                            isCurrentlyActive: Boolean
//                        ) {
//                            super.onChildDraw(
//                                c,
//                                recyclerView,
//                                viewHolder,
//                                dX,
//                                dY,
//                                actionState,
//                                isCurrentlyActive
//                            )
//
//                            val itemView = viewHolder.itemView
////                val background = ColorDrawable(getResources().getColor(R.color.delete_red))
//                            val background = getResources().getDrawable(Color.RED)
//
//
//                            val deleteIcon = activity?.let {
//                                AppCompatResources.getDrawable(
//                                    it,
//                                    Color.RED
//                                )
//                            }
//
//                            val iconMarginVertical = (viewHolder.itemView.height - deleteIcon!!.intrinsicHeight) /2
//                            deleteIcon.setBounds(
//                                itemView.left + iconMarginVertical,
//                                itemView.top + iconMarginVertical,
//                                itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
//                                itemView.bottom - iconMarginVertical
//                            )
//
//                            background.setBounds(
//                                itemView.left,
//                                itemView.top,
//                                itemView.right + dX.toInt(),
//                                itemView.bottom
//                            )
//                            background.draw(c)
//                            deleteIcon.draw(c)
//                        }
//
//                    })
//
//                val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(adapter = TaskRecyclerviewAdapter(
//                    requireContext()
//                ))
//                swipeToDismissTouchHelper.attachToRecyclerView(recyclerViewTask)
//
//            }
//
//
//
//
//
//        }



    }


}