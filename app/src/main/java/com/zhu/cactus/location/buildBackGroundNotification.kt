package com.zhu.cactus.location

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.zhu.cactus.R

val NOTIFICATION_CHANNEL_NAME = "BackgroundLocation"
private var notificationManager: NotificationManager? = null
var isCreateChannel = false

@SuppressLint("NewApi")
    fun buildBackGroundNotification(context: Context,notifyId:Int) {
        var builder: Notification.Builder? = null
        var notification: Notification? = null
        if (Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            val channelId = context.packageName
            if (!isCreateChannel) {
                val notificationChannel = NotificationChannel(
                    channelId,
                    NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationChannel.enableLights(true) //是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE) //小圆点颜色
                notificationChannel.setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
                notificationManager!!.createNotificationChannel(notificationChannel)
                isCreateChannel = true
            }
            builder = Notification.Builder(context.applicationContext, channelId)
        } else {
            builder = Notification.Builder(context.applicationContext)
        }
        builder.setSmallIcon(R.drawable.icon_cactus_small)
            .setContentTitle("Cactus")
            .setContentText("正在后台运行")
            .setWhen(System.currentTimeMillis())
            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(notifyId, builder.build())
            }
    }