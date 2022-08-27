package app.ishizaki.ryu.devgoal.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Bookmark(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "bookmark_url") val url: String,
    val memo: String,
    val createdTime: Long
)