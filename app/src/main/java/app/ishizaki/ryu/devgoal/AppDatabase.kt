package app.ishizaki.ryu.devgoal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Stopwatch::class, Task::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stopwatchDao(): StopwatchDao
    abstract fun taskDao(): TaskDao

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