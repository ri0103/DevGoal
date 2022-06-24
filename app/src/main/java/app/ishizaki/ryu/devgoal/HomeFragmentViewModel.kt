package app.ishizaki.ryu.devgoal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Entity

class HomeFragmentViewModel (app: Application): AndroidViewModel(app) {
//    lateinit var allTasks: MutableLiveData<List<Task>>
//
//    init {
//        allTasks = MutableLiveData()
//    }
//
//    fun getAllTasksObservers(): MutableLiveData<List<Task>> {
//        return allTasks
//    }
//
//    fun getAllTasks() {
//        val taskDao = AppDatabase.getAppDatabase(getApplication())?.taskDao()
//        val list = taskDao?.getAll()
//
//        allTasks.postValue(list)
//    }
//
//    fun insertTask(entity: Task) {
//        val taskDao = AppDatabase.getAppDatabase(getApplication())?.taskDao()
//        taskDao?.insert(entity)
//        getAllTasks()
//    }
//
//    fun deleteTask(entity: Task) {
//        val taskDao = AppDatabase.getAppDatabase(getApplication())?.taskDao()
//        taskDao?.insert(entity)
//        getAllTasks()
//
//    }

}