package app.ishizaki.ryu.devgoal.fragments

import android.content.Intent
import android.location.GnssAntennaInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.TaskRecyclerviewAdapter
import app.ishizaki.ryu.devgoal.Utils
import app.ishizaki.ryu.devgoal.activities.MainActivity
import app.ishizaki.ryu.devgoal.dataclass.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_edit_task.editTaskEditText
import kotlinx.android.synthetic.main.fragment_edit_task.saveEditTaskButton
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*


class EditTaskFragment : Fragment() {


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
        val taskId = bundle!!.getInt("ID")
        val taskBefore = bundle!!.getString("TASK")
        val taskDoneorNot = bundle!!.getBoolean("DONEORNOT")
        val createdTime = bundle!!.getLong("CREATEDTIME")


        editTaskEditText.setText(taskBefore)

        saveEditTaskButton.setOnClickListener {
            val newText = editTaskEditText.text.toString()
            lifecycleScope.launch(Dispatchers.Default) {
                val updatedTask = Task(
                    taskId,
                    newText,
                    taskDoneorNot,
                    createdTime,
                    System.currentTimeMillis()
                )
                taskDao.update(updatedTask)
            }
            activity?.finish()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            activity?.startActivity(intent)
        }

    }

    private fun removeFragment(){
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }
}