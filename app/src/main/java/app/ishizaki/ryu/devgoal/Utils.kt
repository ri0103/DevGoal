package app.ishizaki.ryu.devgoal

import android.content.Context
import androidx.room.Room
import app.ishizaki.ryu.devgoal.room.AppDatabase

object Utils {

    fun getDatabase(context: Context): AppDatabase{
        return  Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database"
        ).build()
    }





}