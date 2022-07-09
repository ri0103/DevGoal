package app.ishizaki.ryu.devgoal.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.ishizaki.ryu.devgoal.dataclass.Goal

@Dao
interface GoalDao {
    @Query("SELECT * FROM goal")
    fun getAll(): List<Goal>

    @Insert
    fun insertAll(vararg goal: Goal)

    @Insert
    fun insert(goal: Goal)

    @Delete
    fun delete(goal: Goal)
}