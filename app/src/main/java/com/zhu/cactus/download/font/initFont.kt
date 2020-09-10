package com.zhu.cactus.download.font

import android.graphics.Typeface
import android.util.Log
import com.norbsoft.typefacehelper.TypefaceCollection
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.cactus.App
import java.io.File

fun iniFont() {
    val fonts = File(App.context.getExternalFilesDir(null), "fonts")
    if (!fonts.exists() || !File(App.context.getExternalFilesDir(null),
            "fonts/STXINWEI.TTF"
        ).exists()
    ) {//判断文件或文件夹是否存在
        fonts.mkdirs()//创建目录
        //默认下载在cache下
        val saveDir="download"
        val url="https://static01.imgkr.com/temp/4b7dd0cfab0b4d38b33288950f583236.jpg"
        DownloadUtil.get()?.download("https://static01.imgkr.com/temp/4b7dd0cfab0b4d38b33288950f583236.jpg",
                saveDir,
                object : DownloadUtil.OnDownloadListener {
                    override fun onDownloadSuccess() {
                        Log.d("font", "下载完成")
                        File(App.context.externalCacheDir, saveDir+"/"+ url.substring(url.lastIndexOf("/") + 1))
                            .renameTo(File(App.context.getExternalFilesDir(null),"fonts/STXINWEI.TTF")) //移动文件
                    }

                    override fun onDownloading(progress: Int) {
                        Log.d("font", "下载进度：$progress")
                    }

                    override fun onDownloadFailed() {
                        Log.d("font", "下载失败")
                    }
                })

    } else {
        // Initialize typeface helper
        App.typeface = TypefaceCollection.Builder()
            .set(
                Typeface.NORMAL,
                Typeface.createFromFile(File("${App.context.getExternalFilesDir(null)}/fonts/STXINWEI.TTF"))
//                Typeface.createFromAsset(assets, "fonts/STXINWEI.TTF")
            )
            .create()
        TypefaceHelper.init(App.typeface)
    }

}