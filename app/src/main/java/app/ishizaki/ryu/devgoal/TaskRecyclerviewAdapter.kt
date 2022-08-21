package app.ishizaki.ryu.devgoal

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.os.IResultReceiver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import app.ishizaki.ryu.devgoal.dataclass.Task
import app.ishizaki.ryu.devgoal.fragments.EditTaskFragment
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
        val db = Room.databaseBuilder(
            holder.itemView.context,
            AppDatabase::class.java, "database"
        ).allowMainThreadQueries().build()
        val taskDao = db.taskDao()
        val all = taskDao.getAll()
        val task = taskList[position]

//        fun openFragment(fragment: Fragment){
//            if(fragment != null){
//                val transaction = (holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.edittask_fragment_container, fragment)
//                transaction.commit()
//            }
//        }

        if (task.taskDoneOrNot){
            holder.taskTitleTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.taskTitleTextView.paintFlags = 0
        }


        holder.taskTitleTextView.text = task.taskTitle
        holder.taskTitleTextView.isChecked = task.taskDoneOrNot

        holder.deleteTaskButton.setOnClickListener(View.OnClickListener {
//            taskDao.delete(all.get(position))
            taskDao.delete(task)
            listener.onItemClick(task)
        })

        holder.taskTitleTextView.setOnCheckedChangeListener { compoundButton, b ->
//            val updatedTask = Task(all[position].id, all[position].taskTitle, !all[position].taskDoneOrNot, all[position].createdTime, System.currentTimeMillis())
            val updatedTask = Task(task.id, task.taskTitle, !task.taskDoneOrNot, task.createdTime, System.currentTimeMillis())
            taskDao.update(updatedTask)
            listener.onItemClick(task)
        }

        holder.taskTitleTextView.setOnLongClickListener {

            val editTaskFragment = EditTaskFragment()
            val bundle = Bundle()
            bundle.putInt("ID", task.id)
            bundle.putString("TASK", task.taskTitle)
            bundle.putBoolean("DONEORNOT", task.taskDoneOrNot)
            bundle.putLong("CREATEDTIME", task.createdTime)
            editTaskFragment.arguments = bundle

            val transaction = (holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.add(R.id.edittask_fragment_container, editTaskFragment).commit()

        true}
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