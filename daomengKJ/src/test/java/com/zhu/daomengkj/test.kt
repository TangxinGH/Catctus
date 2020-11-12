package com.zhu.daomengkj

import com.zhu.daomengkj.jiami.DES_ECB_Encrypt
import com.zhu.daomengkj.Interceptor.AddHeadersInterceptor
import com.zhu.daomengkj.Interceptor.LoggingInterceptor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

class UnitTest {
      suspend fun doSomethingUsefulOne(): Int {
        delay(3000L) // 假设我们在这里做了一些有用的事
          println("ddfa 夺")
        return 13
    }

      suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // 假设我们在这里也做了一些有用的事
          println( "daf dasfdsffdfhjytrtrr")
        return 29
    }
    @Test
    fun main() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val one =GlobalScope.async {      doSomethingUsefulOne() }
            val two =GlobalScope. async { doSomethingUsefulTwo() }

            println("The answer is ${one.await() }${ two.await()}")

        }
        println("Completed in $time ms")
    }
    @Test
    fun asny() {
        main()
       Thread.sleep(1000*5)
            println("春树暮云 ")

    }

    @Test
    fun inttime(){
      println(
          (100 * System.currentTimeMillis()).toInt()
      ) }
    @Test
    fun testokhttp() {
      val server=  MockWebServer()
          server.enqueue(MockResponse().setBody("code:100"))



        val url = "http://localhost.:8888/"
        val sss =  "account=" +"acc"+ "&pwd=" + DES_ECB_Encrypt("pwd") + "&version=4.3.6"
        //进行缓存
        val httpClientBuilder = OkHttpClient.Builder() //1.创建OkHttpClient对象
        val okHttpClient = httpClientBuilder
            .addInterceptor(AddHeadersInterceptor())
            .addInterceptor(LoggingInterceptor())//增加拦截器
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS). build() //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
//        val time=(100 * System.currentTimeMillis()).toInt()

        val call=  okHttpClient.newCall(Request.Builder().url(url)
            .post(FormBody.Builder().add("account","acc").add("pwd","xxx").add("version","version").build())
            .build()) // //3.创建一个call对象,参数就是Request请求对象
//4.请求加入调度，重写回调方法

        //4.请求加入调度，重写回调方法
        call.enqueue( object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                //请求失败执行的方法
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                //请求成功执行的方法
                val body= response.body?.string()
//                val res= JSONObject(body)
//                if (res["code"] == "100")

                    println(body)

            }
        })
        server.takeRequest()

    }
}