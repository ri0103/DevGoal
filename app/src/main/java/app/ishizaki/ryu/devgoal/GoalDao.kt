package app.ishizaki.ryu.devgoal

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoalDao {
    @Query("SELECT * FROM goal")
    fun getAll(): List<Goal>

    @Query("select * from goal where id = :id")
    fun getGoal(id: Int): Goal

    @Insert
    fun insertAll(vararg goals: Goal)

    @Insert
    fun insert(goal: Goal)

    @Delete
    fun delete(goal: Goal)

    @Update
    fun update(goal: Goal)
}