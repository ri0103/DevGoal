package app.ishizaki.ryu.devgoal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import app.ishizaki.ryu.devgoal.R
import app.ishizaki.ryu.devgoal.TaskRecyclerviewAdapter
import app.ishizaki.ryu.devgoal.Utils
import app.ishizaki.ryu.devgoal.dataclass.Task
import app.ishizaki.ryu.devgoal.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        val db = Utils.getDatabase(applicationContext)
        val taskDao = db.taskDao()

        val taskId = intent.getIntExtra(TaskRecyclerviewAdapter.EXTRA_MESSAGE_ID, 0)
        val taskTitleBefore = intent.getStringExtra(TaskRecyclerviewAdapter.EXTRA_MESSAGE_TASK)
        val taskDoneorNot = intent.getBooleanExtra(TaskRecyclerviewAdapter.EXTRA_MESSAGE_DONEORNOT, true)
        val taskCreatedTime = intent.getLongExtra(TaskRecyclerviewAdapter.EXTRA_MESSAGE_CREATEDTIME, 0)
        editTaskEditText.setText(taskTitleBefore)

        saveEditTaskButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default){
                val updatedTask = Task(taskId, editTaskEditText.text.toString(), taskDoneorNot, taskCreatedTime, System.currentTimeMillis())
                taskDao.update(updatedTask)
            }

            finish()

        }


    }
}