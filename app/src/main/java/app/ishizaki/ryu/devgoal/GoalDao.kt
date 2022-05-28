package app.ishizaki.ryu.devgoal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GoalDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<Goal>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Goal>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Goal

    @Insert
    fun insertAll(vararg users: Goal)

    @Delete
    fun delete(user: Goal)
}