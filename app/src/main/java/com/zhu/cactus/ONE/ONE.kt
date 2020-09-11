package com.zhu.cactus.ONE

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.zhu.cactus.App
import com.zhu.cactus.POJO.Newslist
import com.zhu.cactus.filter.FiltersPagerAdapter
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun getONEFor(Count: Int) {
    //进行缓存
    val httpClientBuilder = OkHttpClient.Builder()
    val cacheFile = File(App.context.externalCacheDir, "OneCache")
    val cache = Cache(cacheFile, 1024 * 1024 * 50)
    httpClientBuilder
        .addNetworkInterceptor(CacheInterceptor())
        .cache(cache)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
    val okHttpClient = httpClientBuilder.build()
    //日期
    val c1 = Calendar.getInstance()
    val pattern = "yyyy-MM-dd"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.CHINA)
    for (x in 0..Count) {
        getONE(
            "http://api.tianapi.com/txapi/one/index?key=121939b0c5048ac82af5fbaa4b1c792e&date=" + simpleDateFormat.format(c1.time),
            x,
            okHttpClient
        )
        c1.add(Calendar.DATE, -1)
    }
}

fun getONE(url: String, position: Int, okHttpClient: OkHttpClient) {
//   val url= "http://api.tianapi.com/txapi/one/index?key=121939b0c5048ac82af5fbaa4b1c792e&date=$date"

    val request = Request.Builder()
        .url(url)
        .build()
    val call: Call = okHttpClient.newCall(request)//异步 线程请求
    call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("请求失败")
        }

        override fun onResponse(call: Call, response: Response) {
//                中.string()方法使用次数超过了一次。
            val result = response.body?.string()
            if (result != null) {
                if (result.contains("\"code\":200") || result.contains("\"code\": 200")) {//请求成功


                            val json = JSONObject(result)
                            val newsList = (json["newslist"] as JSONArray).get(0).toString()
                            val mapper = ObjectMapper().registerModule(KotlinModule())
                            val newslistBean:Newslist= mapper.readValue(newsList)

                            if (FiltersPagerAdapter.OneData.size > position) FiltersPagerAdapter.OneData[position].postValue(newslistBean)

                }
            }

        }
    })
}

//val response = okhttp.newCall(request).execute() //同步会