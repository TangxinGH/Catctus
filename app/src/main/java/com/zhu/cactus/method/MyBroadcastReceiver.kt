package com.zhu.cactus.method

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.net.HttpURLConnection
import java.net.URL

private const val TAG = "MyBroadcastReceiver中的"

/**
 * @Description: 编码时我们总会发现如下变量未被使用的警告提示,
 * 上述代码编译通过且可以运行，但每行前面的“感叹号”就严重阻碍了我们判断该行是否设置的断点了。
 * 这时我们可以在方法前添加 @SuppressWarnings("unchecked") 去除这些“感叹号”。
 */
@Deprecated("因为有NetworkCallbackImpl，且广播不适合开线程")
class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        //FIXME 广播 中不允许开线程,先不用这个了
        //调用发送登录
        sendRequestWithHttpURL()


        StringBuilder().apply {
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Log.d(TAG, "这是广播监听网络变化")
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendRequestWithHttpURL() {

        Thread.sleep(2000)
        //启动一个新协程(阻塞的),也可以通过GlobalScope.launch启动一个顶层协程，这样就不用下面的切换线程操作

        /* TODO 通过协程调度器把这段代码切换到线程池中的线程中去执行,runBlocking还是运行在主线程中
         在Android4.0以后，会发现，只要是写在主线程（就是Activity）中的HTTP请求，运行时都会报错，这是因为Android
         在4.0以后为了防止应用的ANR（Aplication Not Response）异常，Android这个设计是为了防止网络请求时间过长而导致界面假死的情况发生。*/

        /*TODO 为保证用户数据和设备的安全，Google针对下一代 Android 系统(Android P) 的应用程序，将要求默认使用加密连接，
        这意味着 Android P 将禁止 App 使用所有未加密的连接，因此运行 Android P 系统的安卓设备无论是接收或者发送流量，
        未来都不能明码传输，需要使用下一代(Transport Layer Security)传输层安全协议，而 Android Nougat 和 Oreo 则不受影响。
        因此在Android P 使用HttpUrlConnection进行http请求会出现以下异常：
        W/System.err: java.io.IOException: Cleartext HTTP traffic to **** not permitted
        使用OKHttp请求则出现：
        java.net.UnknownServiceException: CLEARTEXT communication ** not permitted by network security policy
        在Android P系统的设备上，如果应用使用的是非加密的明文流量的http网络请求，则会导致该应用无法进行网络请求，
        https则不会受影响，同样地，如果应用嵌套了webview，webview也只能使用https请求。

        针对这个问题，有以下三种解决方法：
        （1）APP改用https请求
        （2）targetSdkVersion 降到27以下
        （3）更改网络安全配置 */

        var connection: HttpURLConnection? = null
        try {
            connection = (URL("http://172.16.2.2/drcom/login?callback=dr1003&DDDDD=3172052051626&upass=123456&0MKKey=123456&R1=0&R3=0&R6=0&para=00&v6ip=&v=").openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"//设置请求方式
                connectTimeout = 8000//设置连接超时时间
                readTimeout = 8000//设置读取超时时间
            }

            connection.inputStream.bufferedReader().useLines {
                it.forEach {
                    val sb = StringBuilder().apply {
                        append(it)
                    }
                    Log.d(TAG, "onCreate2: $sb")
                    println(sb.toString())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }

    }


}
