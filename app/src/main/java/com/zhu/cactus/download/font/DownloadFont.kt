package com.zhu.cactus.download.font

import androidx.lifecycle.MutableLiveData
import com.zhu.cactus.App
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream



class DownloadUtil private constructor() {
    private val okHttpClient: OkHttpClient = OkHttpClient()



    /**
     * @param url 下载连接
     * @param saveDir 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    fun download(
        url: String,
        saveDir: String,
        listener: OnDownloadListener
    ) {
        val request: Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                // 下载失败
                listener.onDownloadFailed()
            }


            @Throws(IOException::class)
          override  fun onResponse(call: Call, response: Response) {
                var `is`: InputStream? = null
                val buf = ByteArray(2048)
                var len = 0
                var fos: FileOutputStream? = null
                // 储存下载文件的目录
                val savePath = isExistDir(saveDir)
                try {
                    `is` = response.body!!.byteStream()
                    val total = response.body!!.contentLength()
                    val file = File(savePath, getNameFromUrl(url))
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    while (`is`.read(buf).also({ len = it }) != -1) {
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        // 下载中
                        listener.onDownloading(progress)
                    }
                    fos.flush()
                    // 下载完成
                    listener.onDownloadSuccess()
                } catch (e: Exception) {
                    listener.onDownloadFailed()
                } finally {
                    try {
                        `is`?.close()
                    } catch (e: IOException) {
                    }
                    try {
                        fos?.close()
                    } catch (e: IOException) {
                    }
                }
            }



        })
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException
     * 判断下载目录是否存在
     */
    @Throws(IOException::class)
    private fun isExistDir(saveDir: String): String {
        // 下载位置
        val downloadFile = File(App.context.externalCacheDir.toString() , saveDir)
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile()
        }
        return downloadFile.absolutePath
    }

    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    private fun getNameFromUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

    interface OnDownloadListener {
        /**
         * 下载成功
         */
        fun onDownloadSuccess()

        /**
         * @param progress
         * 下载进度
         */
        fun onDownloading(progress: Int)

        /**
         * 下载失败
         */
        fun onDownloadFailed()
    }

    companion object {
        private var downloadUtil: DownloadUtil? = null
        fun get(): DownloadUtil? {
            if (downloadUtil == null) {
                downloadUtil =
                    DownloadUtil()
            }
            return downloadUtil
        }
    }

}