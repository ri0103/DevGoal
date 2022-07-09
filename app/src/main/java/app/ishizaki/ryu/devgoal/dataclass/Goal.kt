package app.ishizaki.ryu.devgoal.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Goal(
    @PrimaryKey val id: Int,
    @ColumnInfo val goalText: String
//    val : Date,
)