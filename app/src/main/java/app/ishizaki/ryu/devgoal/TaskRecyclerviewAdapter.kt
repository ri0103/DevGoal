package app.ishizaki.ryu.devgoal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import app.ishizaki.ryu.devgoal.dataclass.Task
import app.ishizaki.ryu.devgoal.room.AppDatabase

class TaskRecyclerviewAdapter(context: Context): RecyclerView.Adapter<TaskRecyclerviewAdapter.TaskViewHolder>() {


    //    var items = ArrayList<Task>()
    val taskList: MutableList<Task> = mutableListOf()
    private lateinit var listener: OnTaskCellClickListener

//    fun setListData(data: ArrayList<Task>){
//        this.items = data
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_task_cell, parent, false)
        return TaskViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskTitleTextView.text = task.taskTitle
        holder.taskTitleTextView.isChecked = task.taskDoneOrNot

        holder.deleteTaskButton.setOnClickListener(View.OnClickListener {

            val db = Room.databaseBuilder(
                holder.itemView.context,
                AppDatabase::class.java, "database"
            ).allowMainThreadQueries().build()
            val taskDao = db.taskDao()
            val all = taskDao.getAll()
            taskDao.delete(all.get(position))
            listener.onItemClick(task)

        })

        holder.taskTitleTextView.setOnCheckedChangeListener { compoundButton, b ->
            val db = Room.databaseBuilder(
                holder.itemView.context,
                AppDatabase::class.java, "database"
            ).allowMainThreadQueries().build()
            val taskDao = db.taskDao()
            val all = taskDao.getAll()
            val checkTask = Task(all[position].id, all[position].taskTitle, !all[position].taskDoneOrNot)
            taskDao.update(checkTask)
            listener.onItemClick(task)
        }



    }

    interface  OnTaskCellClickListener {
        fun onItemClick(task: Task)
    }

    fun setOnTaskCellClickListener(listener: OnTaskCellClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun update(list: List<Task>){
        taskList.clear()
        taskList.addAll(list)
        notifyDataSetChanged()
    }

    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val taskTitleTextView: CheckBox = view.findViewById(R.id.taskTextView)
        val deleteTaskButton: ImageButton = view.findViewById(R.id.deleteTaskButton)

    }


}