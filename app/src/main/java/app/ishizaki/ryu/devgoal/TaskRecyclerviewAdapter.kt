package app.ishizaki.ryu.devgoal

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ViewSwitcher
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import app.ishizaki.ryu.devgoal.dataclass.Task
import app.ishizaki.ryu.devgoal.room.AppDatabase
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TaskRecyclerviewAdapter(context: Context): RecyclerView.Adapter<TaskRecyclerviewAdapter.TaskViewHolder>() {


    val taskList: MutableList<Task> = mutableListOf()
    private lateinit var listener: OnTaskCellClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_task_cell, parent, false)
        return TaskViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val db = Room.databaseBuilder(
            holder.itemView.context,
            AppDatabase::class.java, "database"
        ).allowMainThreadQueries().build()
        val taskDao = db.taskDao()
        val task = taskList[position]


        if (task.taskDoneOrNot){
            holder.taskTitleTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.taskTitleTextView.paintFlags = 0
        }


        holder.taskTitleTextView.text = task.taskTitle
        holder.taskTitleTextView.isChecked = task.taskDoneOrNot

        holder.deleteTaskButton.setOnClickListener(View.OnClickListener {
            taskDao.delete(task)
            listener.onItemClick(task)
        })

        holder.taskTitleTextView.setOnClickListener {
            val updatedTask = Task(
                task.id,
                task.taskTitle,
                !task.taskDoneOrNot,
                task.createdTime,
                System.currentTimeMillis()
            )
            taskDao.update(updatedTask)
            listener.onItemClick(task)
        }

        holder.taskTitleTextView.setOnLongClickListener {

            holder.taskViewSwitcher.showNext()
            holder.editTaskEditText.setText(task.taskTitle)

        true}

        holder.editTaskInputLayout.setEndIconOnClickListener{
            val newText = holder.editTaskEditText.text.toString()
            val updatedTask = Task(
                task.id,
                newText,
                task.taskDoneOrNot,
                task.createdTime,
                System.currentTimeMillis()
            )
            taskDao.update(updatedTask)
            holder.taskViewSwitcher.showNext()
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
        val editTaskEditText: TextInputEditText = view.findViewById(R.id.editTaskEditText)
        val taskViewSwitcher: ViewSwitcher = view.findViewById(R.id.taskViewSwitcher)
        val editTaskInputLayout: TextInputLayout = view.findViewById(R.id.editTaskInputLayout)
    }


}