package com.zhu.cactus.download.font

import android.graphics.Typeface
import android.util.Log
import com.norbsoft.typefacehelper.TypefaceCollection
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.cactus.App
import java.io.File
import java.io.FilenameFilter

fun fileFilterEmpty(dir:File) :Boolean {
    return run {
        val fileTree: FileTreeWalk = dir.walk()
        fileTree.maxDepth(1) //需遍历的目录层次为1，即无须检查子目录
            .filter {   it.isFile  }
            .filter {  it.extension in listOf("ttf","TTF") }
            .toList().isEmpty()
    }
}

fun iniFont() {
    val fonts = File(App.context.getExternalFilesDir(null), "fonts")
    if (!fonts.exists() || fileFilterEmpty(fonts)) {//判断文件或文件夹是否存在
      if (!fonts.exists())  fonts.mkdirs()//创建目录
        //默认下载在cache下
        val saveDir="download"
        val url2="https://static01.imgkr.com/temp/b26a56a1660d481a88ecf4f7acebf2d9.jpg"
        val url1="https://static01.imgkr.com/temp/4b7dd0cfab0b4d38b33288950f583236.jpg"
        val url=url2
        DownloadUtil.get()?.download(url,
                saveDir,
                object : DownloadUtil.OnDownloadListener {
                    override fun onDownloadSuccess() {
                        Log.d("font", "下载完成")
                        File(App.context.externalCacheDir, saveDir+"/"+ url.substring(url.lastIndexOf("/") + 1))
                            .renameTo(File(App.context.getExternalFilesDir(null),"fonts/"+System.currentTimeMillis()+".TTF")) //移动文件
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
                    Typeface.createFromFile(fonts.walk().maxDepth(1).filter {   it.isFile  }
                        .filter {  it.extension in listOf("ttf","TTF") }.first())
//                Typeface.createFromAsset(assets, "fonts/STXINWEI.TTF")    File("${App.context.getExternalFilesDir(null)}/fonts/STXINWEI.TTF")
                )
                .create()
            TypefaceHelper.init(App.typeface)
    }
}