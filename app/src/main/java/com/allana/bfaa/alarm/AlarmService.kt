package com.allana.bfaa.alarm

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.allana.bfaa.R
import com.allana.bfaa.view.MainActivity
import java.util.*

class AlarmService: BroadcastReceiver() {

    companion object {
        private const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        showNotification(context)
    }

    private fun showNotification(context: Context?) {
        val channelId = "channel_1"
        val channelName = "alarm_repeat_channel"

        val title = context?.getString(R.string.app_name)
        val message = context?.getString(R.string.message)

        val intentNotification = Intent(context, MainActivity::class.java)

        /**
         * back to main activity from notification
         */
        val pendingIntent = TaskStackBuilder.create(context)
            .addParentStack(MainActivity::class.java)
            .addNextIntent(intentNotification)
            .getPendingIntent(ID_REPEATING, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_baseline_alarm)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.white))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(ID_REPEATING, notification)

    }

    fun setRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmService::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, context.getString(R.string.alarm_on), Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmService::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, context.getString(R.string.alarm_off), Toast.LENGTH_SHORT).show()

    }

}