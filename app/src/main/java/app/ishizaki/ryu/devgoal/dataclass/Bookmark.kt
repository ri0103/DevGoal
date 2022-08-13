package app.ishizaki.ryu.devgoal.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val url: String,
    val memo: String,
    val createdTime: Long
)