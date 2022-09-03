package app.ishizaki.ryu.devgoal.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Stopwatch(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val endDateTime: Date,
    @ColumnInfo(name = "onlydate") val endDateOnly: Date,
    val stopwatchDuration : Double,
    val concentrationLevel: Int,
    val commitUrl: String
)