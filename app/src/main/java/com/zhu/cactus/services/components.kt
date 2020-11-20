package com.zhu.cactus.services

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.zhu.cactus.App
import com.zhu.cactus.R
import com.zhu.cactus.download.images.NOTIFBitmap
import com.zhu.cactus.location.uploadAddress
import com.zhu.cactus.method.NetworkCallbackImpl
import com.zhu.cactus.utils.NotificationUtils
import com.zhu.cactus.utils.isApkInDebug
import com.zhu.daomengkj.notifiTask
import java.util.*

class network : component_impl {

    override fun start() {
        val networkCallback =
            NetworkCallbackImpl()
        val builder = NetworkRequest.Builder()
        val request = builder.build()
        val connMgr =
            App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connMgr.registerNetworkCallback(request, networkCallback)
    }

}

class Location : component_impl {
    companion object {
        var sleepTime = 3600000L//一小时
        var lastLongitude = 0.0
        var lastLatitude = 0.0
        var lastSpeed = 0.0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun start() {
        val t = Thread {
            while (true) {
                uploadAddress(App.context)//先执行再睡眠
                if (isApkInDebug(App.context))
                    Thread.sleep(5000)//test
                else
                    Thread.sleep(sleepTime)
            }
        }
        t.isDaemon = true

        t.priority = 1
        t.start()
    }

}

class daomengNotifi : component_impl {
    override fun start() {
        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    println("开始任务")

                    val sharedPreference =
                        App.context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
                    val boolean = sharedPreference.getString(
                        "username", null
                    ) != null || sharedPreference.getString("password", null) != null
                    if (!boolean) {
                        println("未登录")
                        return
                    }

                    notifiTask(App.context, callback = { value, name ->

//                    showNotification(App.context,"8956","到梦有活动",value,int+8756)
                        val notificationUtils = NotificationUtils(App.context)
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)

                        val bigTextStyle: Notification.BigTextStyle =
                            Notification.BigTextStyle()
                        bigTextStyle.setBigContentTitle("到梦空间活动")
                            .setSummaryText(name)
                            .bigText(value)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //设置优先级
                            notificationUtils.setPriority(NotificationManager.IMPORTANCE_MAX)
                        } else notificationUtils.setPriority(Notification.PRIORITY_MAX)
                        notificationUtils.setStyle( //见官网  https://developer.android.com/training/notify-user/expanded
                            Notification.MediaStyle()
//                        bigTextStyle
                        )
//        BitmapFactory.decodeResource(App.context.resources, R.raw.girl)
                            .setLargeIcon(NOTIFBitmap.bitmap)


                        notificationUtils.sendNotification(
                            Random().nextInt(10) + 8756,
                            name,
                            value,
                            R.mipmap.ic_launcher
                        )

                    })//任务
                }

            },
            1000 * 60 * 10,
            1000 * 60 * 60 * 2   ////在1秒后执行此任务,每次间隔2秒执行一次,如果传递一个Data参数,就可以在某个固定的时间执行这个任务
        )//两小时
        println("任务启动CXCXC ")
    }

}