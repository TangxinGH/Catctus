package com.zhu.daomengkj

import DES_ECB_Encrypt
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.Test
import java.io.IOException
import java.util.concurrent.TimeUnit

class UnitTest {
    @Test
    fun testokhttp() {
      val server=  MockWebServer()
          server.enqueue(MockResponse().setBody("code:100"))



        val url = "https://appdmkj.5idream.net/v2/login/phone"
        val sss =  "account=" +"acc"+ "&pwd=" +DES_ECB_Encrypt("pwd")+ "&version=4.3.6"
        //进行缓存
        val httpClientBuilder = OkHttpClient.Builder() //1.创建OkHttpClient对象
        val okHttpClient = httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS). build() //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        val call=  okHttpClient.newCall(Request.Builder().url(url).header( ).method("POST", sss).build()) // //3.创建一个call对象,参数就是Request请求对象
//4.请求加入调度，重写回调方法

        //4.请求加入调度，重写回调方法
        call.enqueue( object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                //请求失败执行的方法
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                //请求成功执行的方法
                val body=   response.body.toString()
                val res= JSONObject(body)
                if (res["code"] == "100")

                    println(res)

            }
        })
        server.takeRequest()

    }
}