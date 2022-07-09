package app.ishizaki.ryu.devgoal.room

import androidx.room.*
import app.ishizaki.ryu.devgoal.dataclass.Goal

@Dao
interface GoalDao {
    @Query("SELECT * FROM goal")
    fun getAll(): List<Goal>

    @Insert
    fun insertAll(vararg goal: Goal)

    @Insert
    fun insert(goal: Goal)

    @Update
    fun update(goal: Goal)

    @Delete
    fun delete(goal: Goal)
}