package com.zhu.cactus.location

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.zhu.cactus.App
import com.zhu.cactus.R
import com.zhu.cactus.download.images.NOTIFBitmap
import com.zhu.cactus.services.showNotification
import com.zhu.cactus.utils.isApkInDebug
import com.zhu.cactus.utils.NotificationUtils
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.FileWriter
import java.io.IOException


object LocationClient {
    //    companion object {
    //静态包裹 Kotlin类不支持静态方法和成员，但Kotlin支持全局函数和变量 : class 类名 改为 object 类名 即可
    fun okHttpSendByGet(url: String) {
        if (isApkInDebug(App.context)) return//调试用阻断网络
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
//            val response = client.newCall(request).execute() 同步会
        val call: okhttp3.Call = client.newCall(request)//异步 线程请求
        call.enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println("请求失败")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
//                中.string()方法使用次数超过了一次。
//                d("okhttp",response.body?.string().toString())
                if (isApkInDebug(App.context)) App.log_Print.postValue(App.log_Print.value + "\n\n" + response.body?.string())
            }

        })
    }

    /*android得到位置 */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocation(context: Context): Location? {
        val locMan = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!locMan.isLocationEnabled) showMediaStyleNotification(100,"需要位置信息","点击打开位置信息") //位置信息关了

//        way.text = "通过GPS定位"
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
//             ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),1)
            showNotification(App.context,"大视图","打开权限设置","点击打开权限",566)

            return null //退出
        }

        buildBackGroundNotification(context, 69)//后台通知
        var location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location == null) {
            Log.d("位置信息", context.externalCacheDir.toString() + "/GPSLOG.txt")
            val writer = FileWriter(context.externalCacheDir.toString() + "/GPSLOG.txt")

            writer.append("通过网络定位")
            writer.flush()
            writer.close()
            location = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }

        val mNotificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.cancel(69)

        return location

    }

    private fun showMediaStyleNotification(notifyId:Int,title:String,content:String) {

        val notificationUtils = NotificationUtils(App.context)
        //创建intent
        val resultIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //添加为栈顶Activity

        resultIntent.putExtra("what", 3)
        val resultPendingIntent = PendingIntent.getActivity(
            App.context,
            3,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        //发送pendingIntent
        notificationUtils
            .setContentIntent(resultPendingIntent)//设置内容点击处理intent
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置优先级
            notificationUtils.setPriority(NotificationManager.IMPORTANCE_MAX)
        } else notificationUtils.setPriority(Notification.PRIORITY_MAX)
        notificationUtils.setStyle( //见官网  https://developer.android.com/training/notify-user/expanded
            Notification.MediaStyle()
        )
//        BitmapFactory.decodeResource(App.context.resources, R.raw.girl)
            .setLargeIcon(NOTIFBitmap.bitmap)

        notificationUtils.sendNotification(
            notifyId,
            title,
            content,
            R.mipmap.ic_launcher
        )
    }

}