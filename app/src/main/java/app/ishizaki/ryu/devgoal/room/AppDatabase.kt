package app.ishizaki.ryu.devgoal.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.ishizaki.ryu.devgoal.Converters
import app.ishizaki.ryu.devgoal.dataclass.*

@Database(entities = [Stopwatch::class, Task::class, Goal::class, Notifdata::class, Bookmark::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stopwatchDao(): StopwatchDao
    abstract fun taskDao(): TaskDao
    abstract fun goalDao(): GoalDao
    abstract fun notifdataDao(): NotifdataDao
    abstract fun bookmarkDao(): BookmarkDao

}