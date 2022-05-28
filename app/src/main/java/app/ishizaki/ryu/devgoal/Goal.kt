package app.ishizaki.ryu.devgoal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Goal(
    @PrimaryKey val uid: Int,
    val goalWord: String,
    @ColumnInfo(name = "goal_date") val goalDate: Date,
    )