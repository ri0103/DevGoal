package app.ishizaki.ryu.devgoal.room

import androidx.room.*
import app.ishizaki.ryu.devgoal.dataclass.Goal
import app.ishizaki.ryu.devgoal.dataclass.Notifdata

@Dao
interface NotifdataDao {
    @Query("SELECT * FROM notifdata")
    fun getAll(): List<Notifdata>

    @Insert
    fun insertAll(vararg notifdata: Notifdata)

    @Insert
    fun insert(notifdata: Notifdata)

    @Update
    fun update(notifdata: Notifdata)

    @Delete
    fun delete(notifdata: Notifdata)
}