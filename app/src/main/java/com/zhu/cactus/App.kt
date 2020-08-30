package com.zhu.cactus

import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.gyf.cactus.callback.CactusCallback
import com.gyf.cactus.ext.cactus
import com.norbsoft.typefacehelper.TypefaceCollection
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.cactus.method.NetworkCallbackImpl


class App: Application(),CactusCallback {
    companion object {
        var sno = "3172052051626"
        var password = "123456"
        val log_Print = MutableLiveData<String>()
    }

    override fun onCreate() {
        super.onCreate()

// Initialize typeface helper
        // Initialize typeface helper
        val typeface = TypefaceCollection.Builder()
            .set(
                Typeface.NORMAL,
                Typeface.createFromAsset(assets, "fonts/STXINWEI.TTF")
            )
            .create()
        TypefaceHelper.init(typeface)

        //可选，设置通知栏点击事件
        val pendingIntent =
            PendingIntent.getActivity(this, 0, Intent().apply {
                setClass(this@App, MainActivity::class.java)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }, PendingIntent.FLAG_UPDATE_CURRENT)

        cactus {
            //可选，设置通知栏点击事件
            setPendingIntent(pendingIntent)
            setBackgroundMusicEnabled(true)
            //可选，设置奔溃可以重启，google原生rom android 10以下可以正常重启
            setCrashRestartUIEnabled(true)
            //可选，运行时回调
            addCallback(this@App)
            //可选，切后台切换回调
            addBackgroundCallback {
                Toast.makeText(this@App, if (it) "退到后台啦" else "跑到前台啦", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun doWork(times: Int) {
        log_Print.postValue("dowork enter")
//另一种方法
        val networkCallback =
            NetworkCallbackImpl()
        val builder = NetworkRequest.Builder()
        val request = builder.build()
        val connMgr =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connMgr?.registerNetworkCallback(request, networkCallback)
    }

    override fun onStop() {
//        TODO("Not yet implemented")
    }
//    https://juejin.im/entry/6844903496806825998  Application的介绍 单例模式 Android系统的入口是Application类的 onCreate（），默认为空实现
}