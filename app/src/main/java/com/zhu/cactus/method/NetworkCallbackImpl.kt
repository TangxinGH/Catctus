package com.zhu.cactus.method

import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_CAPTIVE_PORTAL
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.zhu.cactus.App
import com.zhu.cactus.App.Companion.password
import com.zhu.cactus.App.Companion.sno
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL
import kotlin.random.Random


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class NetworkCallbackImpl : NetworkCallback() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        //XXX NetworkCapabilities.NET_CAPABILITY_VALIDATED 这个是上网 人标记
//FIXME networkCapabilities.hasCapability  验证上否连     因此可以通过判断这个标记是否存在来判断网络的连通性。通过如下调用来判断标记存在与否
//TODO 因为我们要登录wifi，所以要非
        if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    /*TODO 也就是说，这个网络的标志是否出现在这个实例里面，has*/
                    Log.i("wifi", "当前是wifi，且不能上网，所以进来了")
                    Log.i("TAG", "onCapabilitiesChanged: 网络类型为wifi")
                    Log.d("wifi信息:", networkCapabilities.toString())
                    //FIXME 从打开到连上有四次进来？？
                    App.log_Print.postValue(App.log_Print.value+"\n\n"+networkCapabilities.toString())
                    Log.e(
                        "进来次数",
                        "登录需要:" + networkCapabilities.hasCapability(NET_CAPABILITY_CAPTIVE_PORTAL)
                    )
                    Log.e("是否有mssID", networkCapabilities.toString().indexOf("mSSID").toString())
        //判断ping
                    val ret = null
                    val runtime = Runtime.getRuntime()
                    try {
                        val p = runtime.exec("ping -c 3 www.baidu.com")
                        val ret = p.waitFor()
                        //                如果不通 则 发送数据 在需要网页认证的wifi下：1
//                        在wifi打开但没有网络连接，数据也不可用的状态下   在不可用wifi下  ret=2
                        App.log_Print.postValue(App.log_Print.value+"\n\n ret="+ret)

                        if (ret == 1) {
                            val dr100n = Random.nextInt(1, 15)
                            send("http://172.16.2.2/drcom/login?callback=dr100" + dr100n + "&DDDDD=" + sno + "&upass=" + password + "&0MKKey=" + password + "&R1=0&R3=0&R6=0&para=00&v6ip=&v=")
                        }
                        Log.d("Avalible ping", "Process:$ret")
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }


                }
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.d("TAG", "onCapabilitiesChanged: 蜂窝网络")

                }
                else -> {
                    Log.d("TAG", "onCapabilitiesChanged: 其他网络")
                }
            }
        }
    }

    private fun sendRequestWithHttpURL() {

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
            val account = 141213
            val password = 123456
            connection =
                (URL("http://172.16.2.2/drcom/login?callback=dr1003&DDDDD=" + account + "&upass=" + password + "&0MKKey=123456&R1=0&R3=0&R6=0&para=00&v6ip=&v=").openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"//设置请求方式
                connectTimeout = 8000//设置连接超时时间
                readTimeout = 8000//设置读取超时时间
            }

            connection.inputStream.bufferedReader().useLines {
                it.forEach {
                    val sb = StringBuilder().apply {
                        append(it)
                    }

                    Log.d("这是networkImpl中的", "onCreate2: $sb")
                    println(sb.toString())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }

    }

    private fun send(baseurl: String) {
        //开启线程，发送请求
        Thread(Runnable {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            try {
                val url = URL(baseurl)
                connection = url.openConnection() as HttpURLConnection
                //设置请求方法
                connection.requestMethod = "GET"
                //设置连接超时时间（毫秒）
                connection!!.connectTimeout = 5000
                //设置读取超时时间（毫秒）
                connection.readTimeout = 5000

                //返回输入流
                val `in`: InputStream = connection.inputStream

                //读取输入流
                reader = BufferedReader(InputStreamReader(`in`, "GB2312"))
                val result = StringBuilder()
                var line: String = "saffaf"
                while (true) {
/*             注意line = bufferedReader.readLine()?:break中的line = bufferedReader.readLine()它实际上是一个赋值语句，
              它并没有把line的值作为返回值。这个和java不一样。所以不能在while中直接写 while((line = bufferedReader.readLine()) != null),
              所以上面的代码直接写为while(true)了。
 */
                    line = reader.readLine() ?: break
                    result.append(line)
                }
                println("返回结果：？" + result.toString())
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: ProtocolException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                connection?.disconnect()
            }
        }).start()
    }
}