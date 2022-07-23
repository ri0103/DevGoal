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


//object NotificationId {
//    private val atmi: AtomicInteger = AtomicInteger(0)
//    val iD: Int
//        get() = atmi.incrementAndGet()
//}
//
//object NotificationId {
//    val today = LocalDate.now()
//    val idFormatter = SimpleDateFormat("yyyyMMdd")
//    val iD: Int
//        get() = idFormatter.format(today).toInt()
//}

val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"



class Notification : BroadcastReceiver()
{

    override fun onReceive(context: Context, intent: Intent) {

//        // Create an Intent for the activity you want to start
//        val stwIntent = Intent(context, StopwatchActivity::class.java)
//        // Create the TaskStackBuilder
//        val stwPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
//            // Add the intent, which inflates the back stack
//            addNextIntentWithParentStack(stwIntent)
//            // Get the PendingIntent containing the entire back stack
//            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//        }

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentTitle("開発通知")
            .setContentText("今日も開発しましょう！")
//            .setContentIntent(stwPendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}