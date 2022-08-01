package app.ishizaki.ryu.devgoal.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val taskTitle: String,
    val taskDoneOrNot: Boolean,
    val createdTime: Long,
    val updatedTime: Long
)