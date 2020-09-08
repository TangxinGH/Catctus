package com.zhu.cactus.location

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.zhu.cactus.App
import com.zhu.cactus.R
import com.zhu.cactus.location.LocationClient.getLocation
import com.zhu.cactus.location.LocationClient.okHttpSendByGet
import com.zhu.cactus.services.Location.Companion.lastLatitude
import com.zhu.cactus.services.Location.Companion.lastLongitude
import com.zhu.cactus.services.Location.Companion.lastSpeed
import com.zhu.cactus.services.Location.Companion.sleepTime
import com.zhu.cactus.utils.isApkInDebug
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ln
import kotlin.math.pow


@RequiresApi(Build.VERSION_CODES.O)
fun uploadAddress(context: Context){
    val p0: Location = getLocation(context) ?: return //如果 为空返回

    val sharedPref: SharedPreferences = context.getSharedPreferences(
        App.context.getString(R.string.preference_GPS_user_key),
        Context.MODE_PRIVATE
    )
    val user = sharedPref.getString("user", "10086") //默认0

    val buildUrl =
        "http://121.37.129.14:7000/uploadLocation" + "?uid=" + user + "&GPSTime=" +
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(p0.time) +
                "&Latitude=" + p0.latitude +  //经度
                "&Longitude=" + p0.longitude +
                "&Altitude=" + p0.altitude //海拔

    okHttpSendByGet("$buildUrl&version=NewApk")

    //计算下次上传时间
//                其中A点经度，纬度分别为λA和ΦA，B点的经度、纬度分别为λB和ΦB，d为距离。
    val λA = p0.longitude
    val ΦA = p0.latitude
    val λB = lastLongitude
    val ΦB = lastLatitude

    val distance = GetDistance(λA, ΦA, λB, ΦB)
    Log.d("距离：", distance.toString())
    

    val speed = distance / (sleepTime / 1000) //m/s
    val peta = speed - lastSpeed

    sleepTime =
        when {
            peta < 0 -> {
                //减速运动 应该要慢下降
                (sleepTime - 360000L * peta).toLong()//线性下降 理论每次加几分钟
            }
            peta in 0.0..1.0 -> {
                if (speed == 0.0) sleepTime + 600000L//位置不变，加十分钟
                //匀速运动
                else
                    when (distance) {
                        in 0.0..20.0 -> (sleepTime + 36000L * peta.pow(3.0) * 20).toLong() //位置小速度 指数上升
                        in 20.0..1000.0 -> (sleepTime - 72000L * ln(distance)).toLong() //ln(x)x要大于0 最小减2分钟
                        else -> (sleepTime - (distance / 2).pow(2.0)).toLong()//指数下降
                    }
            }
            1 < peta && peta < 6 -> {
                //加速运动中 汽车的加速度？
                sleepTime / 2  //加速度大于 28km/h时 乘法下降 最快？

            }
            peta >= 6 -> {
                //加速运动中
                sleepTime / 3  //加速度大于 21km/h时 乘法下降 最快？
            }
            else -> 3600000L //默认一小时
        }

    if (isApkInDebug(context)) {
        App.log_Print.postValue(App.log_Print.value+"\n\n mSleepTime"+
            "睡眠时间/分钟：\n" + (sleepTime/60000).toString() +
                    " \n 速度： " + speed +
                    "\n peta 速度差为：" + peta +
                    " \n pow3  peta speed: " + peta.pow(3.0) * 25
        )
        Log.d(
            "睡眠时间：",
            sleepTime.toString() +
                    "  速度： " + speed +
                    "  pow3 speed: " + (speed - lastSpeed).pow(3.0) * 25
        )
        Log.d("上次location", "${lastLongitude}，${lastLatitude}")
        Log.d("这次location", "${p0.longitude}，${p0.latitude}")
        Log.d("分隔线：", "===============================================")
    }

    if (sleepTime < 10000) sleepTime = 10000L//最小10秒
    if (sleepTime > 5000000L) sleepTime = 5000000L//最大一小时多点
    lastLongitude = p0.longitude//保存上一次的值
    lastLatitude = p0.latitude
    lastSpeed = speed//保存上一次位移

}