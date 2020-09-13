package com.zhu.cactus

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Environment
import android.system.Os
import androidx.lifecycle.MutableLiveData
import com.gyf.cactus.callback.CactusCallback
import com.gyf.cactus.ext.cactus
import com.norbsoft.typefacehelper.TypefaceCollection
import com.norbsoft.typefacehelper.TypefaceHelper
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.zhu.cactus.download.font.fileFilterEmpty
import com.zhu.cactus.download.font.iniFont
import com.zhu.cactus.download.images.NOTIFBitmap
import com.zhu.cactus.download.images.initImage
import com.zhu.cactus.services.Location
import com.zhu.cactus.services.component_impl
import com.zhu.cactus.services.network
import java.io.File


class App : Application(), CactusCallback {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var sno: String
        lateinit var password: String
        val log_Print = MutableLiveData<String>()
        val COMPONENTS: ArrayList<component_impl> = ArrayList()
        var typeface: TypefaceCollection? = null

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        applicationInit()

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
//                Toast.makeText(this@App, if (it) "退到后台啦" else "跑到前台啦", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun applicationInit() {
        /*bugly init */
        val strategy = UserStrategy(applicationContext)
        strategy.isUploadProcess = getProcessName() == null || getProcessName().equals(packageName)//时增加一个上报进程的策略配置
// 初始化Bugly
//        CrashReport.initCrashReport(applicationContext, "284486e9ec", false,strategy)//第三个参数为SDK调试模式开关
// init sno pass
        val sp = getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE)
        sno = sp.getString("sno", "141213").toString()
        password = sp.getString("pass", "123456").toString()

        val fonts = File(context.getExternalFilesDir(null), "fonts")
        if (!fonts.exists() || fileFilterEmpty(fonts,listOf("ttf","TTF"))) {//判断文件或文件夹是否存在
            val t = Thread {
                iniFont(fonts)
            }
            t.start()

        }
        else {
            // Initialize typeface helper
            typeface = TypefaceCollection.Builder()
                .set(
                    Typeface.NORMAL,
                    Typeface.createFromFile(fonts.walk().maxDepth(1).filter {   it.isFile  }
                        .filter {  it.extension in listOf("ttf","TTF") }.first())
//                Typeface.createFromAsset(assets, "fonts/STXINWEI.TTF")
                )
                .create()
            TypefaceHelper.init(typeface)
        }


        val images = File(context.getExternalFilesDir(null), "Pictures")
        if (!images.exists() || fileFilterEmpty(images,listOf("jpg","png","JPG","PNG"))) {//判断文件或文件夹是否存在
            val t = Thread {
                initImage(images)
            }
            t.start()
        }else {
            NOTIFBitmap.bitmap= BitmapFactory.decodeFile(images.walk().maxDepth(1).filter {   it.isFile  }
                .filter {  it.extension in listOf("jpg","png","JPG","PNG") }.first().absolutePath)

        }
    }


    override fun doWork(times: Int) {
        log_Print.postValue("dowork enter")
        COMPONENTS.add(network()) // plus 是返回新的 数组而不原来的
        COMPONENTS.add(Location())
        COMPONENTS.forEach { components: component_impl -> components.start() }

    }

    override fun onStop() {
//        TODO("Not yet implemented")
    }
//    https://juejin.im/entry/6844903496806825998  Application的介绍 单例模式 Android系统的入口是Application类的 onCreate（），默认为空实现
}