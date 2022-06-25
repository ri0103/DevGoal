package app.ishizaki.ryu.devgoal.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.ishizaki.ryu.devgoal.dataclass.Stopwatch

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