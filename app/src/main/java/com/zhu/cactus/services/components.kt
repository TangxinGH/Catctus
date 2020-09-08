package com.zhu.cactus.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import com.zhu.cactus.App
import com.zhu.cactus.method.NetworkCallbackImpl
import com.zhu.cactus.location.uploadAddress
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
companion object{
    var sleepTime = 3600000L//一小时
    var lastLongitude = 0.0
    var lastLatitude = 0.0
    var lastSpeed = 0.0
}
    @RequiresApi(Build.VERSION_CODES.O)
    override fun start() {
        val t = Thread {
            while (true) {
                if (isApkInDebug(App.context))
                    Thread.sleep(5000)//test
                else
                    Thread.sleep(sleepTime)
                uploadAddress(App.context)
            }
        }
        t.isDaemon = true

        t.priority = 1
        t.start()
    }

}