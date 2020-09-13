package com.zhu.cactus.download.images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Environment
import android.util.Log
import com.zhu.cactus.App
import com.zhu.cactus.App.Companion.context
import com.zhu.cactus.download.font.DownloadUtil
import java.io.File

object NOTIFBitmap {
    var bitmap: Bitmap=Bitmap.createBitmap(1000, 400, Bitmap.Config.ARGB_8888)
 //创建一个默认的白色bitmap
}

fun initImage(images:File) {

        images.mkdirs()//创建目录
        //默认下载在cache下
        val saveDir = "download"
        val url = "https://i.niupic.com/images/2020/09/09/8En5.png"
//         "https://i.niupic.com/images/2020/09/09/8EkH.jpg"

         DownloadUtil.get()?.download(url,
                saveDir,
                object : DownloadUtil.OnDownloadListener {
                    override fun onDownloadSuccess() {
                        Log.d("font", "下载完成")
                   val  oldF=File(context.externalCacheDir,saveDir + "/" + url.substring(url.lastIndexOf("/") + 1))
                    val  newF=File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"NOTIFImage.jpg")
                      oldF.renameTo(newF) //移动文件
                     NOTIFBitmap.bitmap=BitmapFactory.decodeFile(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()+"/NOTIFImage.jpg")
                    }

                    override fun onDownloading(progress: Int) {
                        Log.d("font", "下载进度：$progress")
                    }

                    override fun onDownloadFailed() {
                        Log.d("font", "下载失败")
                    }
                })
}