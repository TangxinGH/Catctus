package com.zhu.daomengkj.update

import android.content.Context
import com.azhon.appupdate.manager.DownloadManager
import com.zhu.daomengkj.R

fun init(context: Context){
    val manager: DownloadManager = DownloadManager.getInstance(context)
    manager.setApkName("Catctus.apk")
        .setApkUrl("https://raw.githubusercontent.com/azhon/AppUpdate/master/apk/appupdate.apk")
        .setSmallIcon(R.mipmap.ic_launcher)
        .download()
}