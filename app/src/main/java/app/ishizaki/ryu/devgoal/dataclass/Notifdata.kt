package app.ishizaki.ryu.devgoal.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notifdata(
    @PrimaryKey val id: Int,
    val notifHour: Int,
    val notifMin: Int,
)