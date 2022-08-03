package app.ishizaki.ryu.devgoal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.Utils
import app.ishizaki.ryu.devgoal.dataclass.Task
import kotlinx.android.synthetic.main.fragment_edit_task.editTaskEditText
import kotlinx.android.synthetic.main.fragment_edit_task.saveEditTaskButton
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class EditTaskFragment : Fragment() {
    // TODO: Rename and change types of parameters


//    private lateinit var listener: OnFinishEditListener
//    interface  OnFinishEditListener {
//        fun onButtonClicked()
//    }
//    fun setOnFinishEditListener(listeners: OnFinishEditListener) {
//        this.listener = listeners
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Utils.getDatabase(requireContext())
        val taskDao = db.taskDao()

        val bundle = arguments
        val id = bundle!!.getInt("ID")
        val taskBefore = bundle!!.getString("TASK")
        val taskDoneorNot = bundle!!.getBoolean("DONEORNOT")
        val createdTime = bundle!!.getLong("CREATEDTIME")

        editTaskEditText.setText(taskBefore)

        saveEditTaskButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                val updatedTask = Task(
                    id,
                    editTaskEditText.text.toString(),
                    taskDoneorNot,
                    createdTime,
                    System.currentTimeMillis()
                )
                taskDao.update(updatedTask)
            }
//            listener.onButtonClicked()
            removeFragment()
        }

    }

    private fun removeFragment(){
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }
}