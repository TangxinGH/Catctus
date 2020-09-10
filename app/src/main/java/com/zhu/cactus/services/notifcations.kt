package com.zhu.cactus.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.zhu.cactus.MainActivity
import com.zhu.cactus.R
import com.zhu.cactus.download.images.NOTIFBitmap
import java.lang.ref.WeakReference
@Deprecated("参考用，mediaStyle样式 NotificationUtils 代替 ")
fun showNotification(context: Context,CHANNEL_ID:String ,ContentTitle:String,ContentText:String,notifyId:Int) {
    val pendingIntent =
        PendingIntent.getActivity(context, 0, Intent().apply {
            setClass(context, MainActivity::class.java)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }, PendingIntent.FLAG_UPDATE_CURRENT)

//        请注意，NotificationCompat.Builder 构造函数要求您提供渠道 ID。这是兼容 Android 8.0（API 级别 26）及更高版本所必需的，但会被较旧版本忽略。
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.icon_cactus_small)
        // Show controls on lock screen even when user hides sensitive content.
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        // Add media control buttons that invoke intents in your media service
//            .addAction(R.drawable.icon_cactus_small, "Previous", pendingIntent) // #0
//            .addAction(R.drawable.icon_cactus_small, "Pause", pendingIntent) // #1
//            .addAction(R.drawable.ic_next, "Next", nextPendingIntent) // #2
        // Apply the media style template
        .setStyle( //见官网  https://developer.android.com/training/notify-user/expanded
            androidx.media.app.NotificationCompat.MediaStyle()
//                    .setShowActionsInCompactView(1 /* #1: pause button \*/)
//                .setMediaSession(mediaSession.getSessionToken())
        )
        .setContentTitle(ContentTitle)
        .setContentText(ContentText)
        .setLargeIcon(NOTIFBitmap.bitmap)
        .setContentIntent(pendingIntent)
    createNotificationChannel(context, CHANNEL_ID)
    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define
        notify(notifyId, builder.build())
    }

}

private fun createNotificationChannel(context: Context,CHANNEL_ID: String) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
//        由于您必须先创建通知渠道，然后才能在 Android 8.0 及更高版本上发布任何通知，因此应在应用启动时立即执行这段代码。反复调用这段代码是安全的，因为创建现有通知渠道不会执行任何操作。
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}