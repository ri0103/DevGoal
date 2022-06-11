package app.ishizaki.ryu.devgoal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StopwatchDao {
    @Query("SELECT * FROM stopwatch")
    fun getAll(): List<Stopwatch>

    @Insert
    fun insertAll(vararg stopwatch: Stopwatch)

    @Insert
    fun insert(stopwatch: Stopwatch)

    @Delete
    fun delete(stopwatch: Stopwatch)
}