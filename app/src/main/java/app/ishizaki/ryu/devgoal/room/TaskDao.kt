package app.ishizaki.ryu.devgoal.room

import androidx.room.*
import app.ishizaki.ryu.devgoal.dataclass.Task

@Dao
interface TaskDao {
//    @Query("SELECT * FROM task")
    @Query("SELECT * FROM task ORDER BY task_is_done ASC")
    fun getAll(): List<Task>

    @Insert
    fun insertAll(vararg task: Task)

    @Insert
    fun insert(task: Task)

    @Update
    fun update(vararg task: Task)

    @Delete
    fun delete(task: Task)

    @Query("delete from task")
    fun deleteAll()

//    @Query("SELECT * FROM task ORDER BY task_is_done ASC")
//    fun getAllSortByIsDone(): List<Task>

}