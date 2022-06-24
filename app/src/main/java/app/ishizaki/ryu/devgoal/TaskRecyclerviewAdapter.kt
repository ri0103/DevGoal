package app.ishizaki.ryu.devgoal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task_cell.view.*

class TaskRecyclerviewAdapter(context: Context): RecyclerView.Adapter<TaskRecyclerviewAdapter.TaskViewHolder>() {

//    var items = ArrayList<Task>()
    val taskList: MutableList<Task> = mutableListOf()

//    fun setListData(data: ArrayList<Task>){
//        this.items = data
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_task_cell, parent, false)
        return TaskViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        holder.bind(items[position])
        val task = taskList[position]
        holder.taskTitleTextView.text = "・" + task.taskTitle
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

        val taskTitleTextView: TextView = view.findViewById(R.id.taskTextView)

//        fun bind(data: Task){
//            taskTitleTextView.text = data.taskTitle
//        }
    }


}