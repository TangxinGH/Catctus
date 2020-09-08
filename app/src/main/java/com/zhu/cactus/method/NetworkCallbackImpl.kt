package com.zhu.cactus.method

import android.icu.text.SimpleDateFormat
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
import com.zhu.cactus.utils.isApkInDebug
import com.zhu.cactus.services.showNotification
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL
import java.util.*
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
                    if (isApkInDebug(App.context))  App.log_Print.postValue(App.log_Print.value+"\n\n"+ SimpleDateFormat("HH:mm:ss.SSS").format(Date())+networkCapabilities.toString())
                    Log.e(
                        "进来次数",
                        "登录需要:" + networkCapabilities.hasCapability(NET_CAPABILITY_CAPTIVE_PORTAL)
                    )
                    Log.e("是否有mssID", networkCapabilities.toString().indexOf("mSSID").toString())
        //判断ping
                    val runtime = Runtime.getRuntime()
                    try {
                        val p = runtime.exec("ping -c 1 www.baidu.com")
                        val ret = p.waitFor()
                        //                如果不通 则 发送数据 在需要网页认证的wifi下：1
//                        在wifi打开但没有网络连接，数据也不可用的状态下   在不可用wifi下  ret=2
                        App.log_Print.postValue(App.log_Print.value+"\n\n ret="+ret)

                        if (ret == 1 || ret==2) {
                            val dr100n = Random.nextInt(1, 15)
                            send("http://172.16.2.2/drcom/login?callback=dr100$dr100n&DDDDD=$sno&upass=$password&0MKKey=$password&R1=0&R3=0&R6=0&para=00&v6ip=&v=")
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
                if (isApkInDebug(App.context))App.log_Print.postValue(App.log_Print.value+"\n\n"+"返回结果：？" + result.toString())

//                val test=" dr1004({\"result\":1,\"aolno\":4616,\"m46\":0,\"v46ip\":\"172.20.65.36\",\"myv6ip\":\"\",\"sms\":0,\"ufee\":0,\"NID\":\"朱晓锋\",\"olno\":0,\"udate\":\"\",\"olmac\":\"000000000000\",\"ollm\":0,\"olm1\":\"00000000\",\"olm2\":\"0000\",\"olm3\":0,\"olmm\":2,\"olm5\":0,\"gid\":4,\"mac1\":\"\",\"mac2\":\"\",\"mac3\":\"\",\"mac4\":\"\",\"mac5\":\"\",\"mac6\":\"\",\"ac0\":\"MzE3MjA1MjA1MTYyNg==\",\"oltime\":4294967295,\"olflow\":4294967295,\"lip\":\"172.20.65.36\",\"stime\":\"2020-09-01 22:03:10\",\"etime\":\"2020-09-01 22:06:15\",\"uid\":\"3172052051626\",\"sv\":0})   "
                val jsonObj = JSONObject(result.toString().replaceBefore("{","").dropLast(1))
                if (jsonObj["result"] == 1) showNotification(
                    App.context,
                    "14",
                    "登录成功",
                    jsonObj["NID"].toString() + jsonObj["v46ip"],
                    200
                )//通知

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