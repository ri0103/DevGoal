package app.ishizaki.ryu.devgoal

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import app.ishizaki.ryu.devgoal.activities.StopwatchActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicInteger



val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"



class Notification : BroadcastReceiver()
{

    override fun onReceive(context: Context, intent: Intent) {

        val stwIntent = Intent(context, StopwatchActivity::class.java)

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("開発通知")
            .setContentText("今日も開発しましょう！")
            .setContentIntent(PendingIntent.getActivity(context, 1, stwIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT))
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}