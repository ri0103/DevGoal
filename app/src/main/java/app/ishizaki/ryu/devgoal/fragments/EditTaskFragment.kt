package app.ishizaki.ryu.devgoal.fragments

import android.content.Context
import android.content.Intent
import android.location.GnssAntennaInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.TaskRecyclerviewAdapter
import app.ishizaki.ryu.devgoal.Utils
import app.ishizaki.ryu.devgoal.activities.MainActivity
import app.ishizaki.ryu.devgoal.dataclass.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_edit_task.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*


class EditTaskFragment : Fragment() {


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

        editTaskInputLayout.setEndIconOnClickListener() {
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

            setFragmentResult("requestKey", bundleOf("resultKey" to "result"))

            fragmentManager?.beginTransaction()?.remove(this@EditTaskFragment)?.commit()

        }

    }

}