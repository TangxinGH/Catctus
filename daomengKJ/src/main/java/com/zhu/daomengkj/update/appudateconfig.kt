package com.zhu.daomengkj.update

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.util.Log
import app_update
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.manager.DownloadManager
import com.zhu.daomengkj.App
import com.zhu.daomengkj.R
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


val logtag=    "output-metadata.json"
/**
 * 文件名
 * */
fun downloadNew(context: Context, appJson: app_update){


    /*
         * 整个库允许配置的内容
         * 非必选
         */
    val configuration = UpdateConfiguration() //输出错误日志
        .setEnableLog(true)
        //设置自定义的下载
        //.setHttpManager()
        //下载完成自动跳动安装页面
        .setJumpInstallPage(true)
        //设置对话框背景图片 (图片规范参照demo中的示例图)
//        .setDialogImage(R.mipmap.ic_dialog)
        //设置按钮的颜色
//        .setDialogButtonColor(Color.parseColor("#E743DA"))
        //设置对话框强制更新时进度条和文字的颜色
//        .setDialogProgressBarColor(Color.parseColor("#E743DA"))
        //设置按钮的文字颜色
        .setDialogButtonTextColor(Color.WHITE) //设置是否显示通知栏进度
        .setShowNotification(true) //设置是否提示后台下载toast
        .setShowBgdToast(false) //设置强制更新
        .setForcedUpgrade(false) //设置对话框按钮的点击监听
//        .setButtonClickListener { id -> Log.e("TAG", id.toString()) } //设置下载过程的监听
//        .setOnDownloadListener(listenerAdapter)

    val manager: DownloadManager = DownloadManager.getInstance(context)
    manager.setApkName(appJson.elements[0].outputFile)
        .setApkUrl("https://raw.fastgit.org/TangXinGithub/Catctus/daomeng/app/release/${appJson.elements[0].outputFile}") //最新的 有缓存，只能改铝箔
//            https://api.github.com/repos/TangXinGithub/Catctus/releases/latest  返回json  assets  tag_name
        .setSmallIcon(R.mipmap.ic_launcher)
        .setConfiguration(configuration)//配置
        .setApkVersionCode(appJson.elements[0].versionCode)// 当当前构建的小于远端的时，则更新，由app_update判断
        .setApkVersionName(appJson.elements[0].versionName)
        .setApkSize(appJson.apkSize.toString())
        .setApkDescription("${appJson.apkInfo}\n更新了功能，修了一些bug")
        .download()
}

/**
 * https://doc.fastgit.org/zh-cn/node.html
 * 有一定的缓存时间 大概是多久不知道
 * */
fun isNew(context: Context){

//    output-metadata.json
//    raw.fastgit.org 无缓存
    val url ="https://raw.fastgit.org/TangXinGithub/Catctus/daomeng/app/release/output-metadata.json"
    val httpClientBuilder = OkHttpClient.Builder() //1.创建OkHttpClient对象

    val okHttpClient = httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build() //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
    val request = Request.Builder().url(url).get().build()
    okHttpClient.newCall(request).enqueue(object : Callback {

        override fun onFailure(call: Call, e: IOException) {
            Log.d("网络失败github 网络直接访问？", e.toString())

        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val `in`: InputStream = response.body!!.byteStream()
                val reader = BufferedReader(InputStreamReader(`in`))
                val content = StringBuilder()
                var line = reader.readLine()
                while (line != null) {
                    content.append(line)
                    line = reader.readLine()
                }
                Log.d(logtag, content.toString())
                try {
                    val json = Json.decodeFromString(app_update.serializer(), content.toString())
                    if (json.elements[0].versionCode > packageCode(context)) App.app_update.postValue(json) else {
                        Log.d(logtag, "已是最新")
                    }
                } catch (e: Exception) {
                    Log.d(logtag, e.toString())
                }
            }
        }
    })

}

/**
 * 获取应用的版本号versionCode：
 * */
fun packageCode(context: Context): Int {
    val manager: PackageManager = context.packageManager
    var code = 0
    try {
        val info: PackageInfo = manager.getPackageInfo(context.packageName, 0)
        code = info.versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return code
}

/**
 * 获取应用的版本信息：

首先需要获取包管理者，然后再获取包对应的信息，再获取版本信息，最后把值设置到该显示的地方就可以了
 */
fun packageName(context: Context): String? {
    val manager = context.packageManager
    var name: String? = null
    try {
        val info = manager.getPackageInfo(context.packageName, 0)
        name = info.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return name
}