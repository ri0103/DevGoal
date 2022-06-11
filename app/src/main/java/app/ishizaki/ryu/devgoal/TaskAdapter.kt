package app.ishizaki.ryu.devgoal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val taskTextView: TextView = view.findViewById(R.id.taskTextView)
    }

//    override fun getItemCount(): Int {
//        return taskList?.size ?: 0
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(
//            R.layout.item_task_cell, parent, false
//        )
//        return ViewHolder(v)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val task: Task = taskList?.get(position) ?: return
//        holder.taskTextView.text = task.taskTitle
//
//    }


}