package app.ishizaki.ryu.devgoal.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.ishizaki.ryu.devgoal.Converters
import app.ishizaki.ryu.devgoal.dataclass.Stopwatch
import app.ishizaki.ryu.devgoal.dataclass.Task
import app.ishizaki.ryu.devgoal.dataclass.Goal

@Database(entities = [Stopwatch::class, Task::class, Goal::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stopwatchDao(): StopwatchDao
    abstract fun taskDao(): TaskDao
    abstract fun goalDao(): GoalDao

//    companion object {
//        private var INSTANCE: AppDatabase?=null
//
//        fun getAppDatabase(context: Context): AppDatabase? {
//            if (INSTANCE == null) {
//
//                INSTANCE = Room.databaseBuilder<AppDatabase>(context.applicationContext, AppDatabase::class.java, "AppDB")
//                    .allowMainThreadQueries()
//                    .build()
//            }
//            return INSTANCE
//        }
//    }
}